package ch.silviowangler.addresses.geocoder.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Silvio Wangler
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findFirstByLongitudeIsNullAndLatitudeIsNull();

    Optional<Location> findFirstByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNull();

    Optional<Location> findFirstByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNullAndZip(String zip);

    long countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNull();

    long countAllByLongitudeIsNotNullAndLatitudeIsNotNullAndProcessedIsNotNull();

    long countAllByLongitudeIsNullAndLatitudeIsNullAndProcessedIsNotNull();

    Optional<Location> findByUuid(String uuid);


}
