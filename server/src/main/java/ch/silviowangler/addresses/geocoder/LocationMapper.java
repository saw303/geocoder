package ch.silviowangler.addresses.geocoder;

import ch.silviowangler.addresses.geocoder.api.LocationVO;
import ch.silviowangler.addresses.geocoder.domain.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

/**
 * @author Silvio Wangler
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mappings({
            @Mapping(target = "id", source = "uuid")
    })
    LocationVO toLocationVo(Location location);

    List<LocationVO> toLocationVOs(List<Location> locations);

    default UUID toUuid(String value) {
        return UUID.fromString(value);
    }
}
