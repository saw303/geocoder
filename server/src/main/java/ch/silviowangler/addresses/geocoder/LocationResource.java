package ch.silviowangler.addresses.geocoder;

import ch.silviowangler.addresses.geocoder.api.LocationVO;
import ch.silviowangler.addresses.geocoder.api.Stats;
import ch.silviowangler.addresses.geocoder.api.ZipStats;
import ch.silviowangler.addresses.geocoder.domain.Location;
import ch.silviowangler.addresses.geocoder.domain.LocationRepository;
import com.google.gson.Gson;
import com.google.maps.model.AddressType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Silvio Wangler
 */

@RestController
@RequestMapping("/v1/location")
public class LocationResource {

	private final LocationRepository locationRepository;
	private final LocationMapper locationMapper;

	private static final Logger log = LoggerFactory.getLogger(LocationResource.class);

	@Autowired
	public LocationResource(LocationRepository locationRepository, LocationMapper locationMapper) {
		this.locationRepository = locationRepository;
		this.locationMapper = locationMapper;
	}

	@GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public LocationVO next(@RequestParam(value = "zip", required = false) String zip) {


		final Optional<Location> location;

		if (zip != null) {
			location = locationRepository.findFirstByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNullAndZip(zip);
		} else {
			location = locationRepository.findFirstByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNull();
		}
		return locationMapper.toLocationVo(location.orElseThrow(NoDataToProcessException::new));
	}

	@GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public Stats fetchStats() {

		Stats stats = new Stats();

		stats.setTotalRecords(locationRepository.count());
		stats.setUnprocessed(locationRepository.countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNull());
		stats.setProcessed(locationRepository.countAllByLongitudeIsNotNullAndLatitudeIsNotNullAndProcessedIsNotNull());
		stats.setProcessedNoGeocodes(locationRepository.countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNotNull());

		return stats;
	}

	@GetMapping(value = "/stats/{zip}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ZipStats fetchStats(@PathVariable("zip") String zip) {

		ZipStats zipStats = new ZipStats();

		zipStats.setZip(zip);
		zipStats.setTotal(locationRepository.countAllByZip(zip));
		zipStats.setUnprocessed(locationRepository.countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNullAndZip(zip));
		zipStats.setProcessed(locationRepository.countAllByProcessedIsNotNullAndZip(zip));

		return zipStats;
	}

	@PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void update(@PathVariable("id") UUID uuid, @RequestBody GeocodingResult[] results) {

		saveToFileSystem(uuid, results);

		final Optional<Location> location = locationRepository.findByUuid(uuid.toString());

		location.ifPresent(l -> {
			Optional<GeocodingResult> result = Arrays.stream(results).filter(a -> Arrays.asList(a.types).contains(AddressType.STREET_ADDRESS)).findAny();

			if (l.getProcessed() == null) {
				l.setProcessed(Instant.now());
			}

			if (result.isPresent()) {
				GeocodingResult geocodingResult = result.get();

				LatLng googleLocation = geocodingResult.geometry.location;
				l.setLongitude(googleLocation.lng);
				l.setLatitude(googleLocation.lat);
			} else if (results.length > 0) {
				LatLng googleLocation = results[0].geometry.location;
				l.setLongitude(googleLocation.lng);
				l.setLatitude(googleLocation.lat);
			} else {
				log.warn("Could not save {} with {}", uuid, results);
			}
			locationRepository.save(l);
		});
	}

	private void saveToFileSystem(UUID uuid, GeocodingResult[] results) {

		String baseDir = System.getProperty("geo.backup.dir", System.getProperty("java.io.tmpdir"));
		String filename = uuid.toString() + "-" + LocalDateTime.now().toString() + ".json";

		File f = new File(baseDir, filename);

		final String s = new Gson().toJson(results);

		try {
			log.info("Wrote file to {}", f.getAbsolutePath());
			Files.write(f.toPath(), s.getBytes());
		} catch (IOException e) {
			log.error("Unable to write file {}", f.getAbsolutePath(), e);
		}
	}
}
