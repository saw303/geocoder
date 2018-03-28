package ch.silviowangler.addresses.geocoder.importer;

import ch.silviowangler.addresses.geocoder.domain.Location;
import ch.silviowangler.addresses.geocoder.domain.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class is importing the address data and bumps it into a database.
 *
 * @author Silvio Wangler
 */
@Component
public class BulkLoader {

    private final Resource jsonResource;

    private final static Logger log = LoggerFactory.getLogger(BulkLoader.class);
    private final LocationRepository locationRepository;
    private final int capacity;

    @Autowired
    public BulkLoader(LocationRepository locationRepository,
                      @Value("${address.lookup.import.capacity:50000}") int capacity,
                      @Value("${address.lookup.import.enabled:true}") boolean enabled) throws IOException

    {

        this.jsonResource = new ClassPathResource("addresses.json");
        this.locationRepository = locationRepository;
        this.capacity = capacity;

        if (enabled && locationRepository.count() == 0) {
            init();
        } else {
            log.info("Address import is disabled");
        }
    }

    private void init() throws IOException {

        Assert.notNull(this.jsonResource, "Json file resource must not be null. Please provide it by using the -Daddress.lookup.json.file.path option");
        Assert.isTrue(this.jsonResource.exists(), String.format("The json file %s does not exist.", this.jsonResource.toString()));
        Assert.isTrue(this.jsonResource.isReadable(), String.format("The json file %s is not readable for this application. Please check the file system permissions.", this.jsonResource.toString()));

        processJson();
    }

    private void processJson() throws IOException {

        long start = System.currentTimeMillis();
        List<Location> records = new ArrayList<>(capacity);

        Consumer<Location> streetRecordConsumer = (r) -> {

            records.add(r);

            if (records.size() == capacity) {
                log.info("About to start bulk import of {} address records...", String.format("%,d", records.size()));
                try {

                    this.locationRepository.saveAll(records);
                }
                catch(Exception e) {
                    log.error("Error inserting stuff", e);

                    for (Location record : records) {
                        try {
                            this.locationRepository.save(record);
                        } catch (Exception e1) {
                            log.error("Not again!", e1);
                        }
                    }

                }
                records.clear();
                log.info("Successfully inserted {} documents", capacity);
            }
        };

        new StreetRecordStreamingParser(jsonResource.getInputStream(), streetRecordConsumer).run();

        // insert the rest
        log.info("About to start address bulk import of {} records...", records.size());
        try {
            this.locationRepository.saveAll(records);
        } catch (Exception e) {
            for (Location record : records) {
                try {
                    this.locationRepository.save(record);
                } catch (Exception e1) {
                    log.error("Not again", e1);
                }
            }

        }
        records.clear();

        long end = System.currentTimeMillis();
        log.info("address json file has been consumed successfully in {} ms", (end - start));
    }
}
