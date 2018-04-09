package ch.silviowangler.addresses.geocoder.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Silvio Wangler
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findFirstByLongitudeIsNullAndLatitudeIsNull();

    Optional<Location> findFirstByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNull();

    Optional<Location> findFirstByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNullAndZip(String zip);

    List<Location> findAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNullAndZip(String zip);

    long countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNull();

    long countAllByLongitudeIsNotNullAndLatitudeIsNotNullAndProcessedIsNotNull();

    long countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNotNull();

	long countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNullAndZip(String zip);
	long countAllByZip(String zip);

    Optional<Location> findByUuid(String uuid);


}
