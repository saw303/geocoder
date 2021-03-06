package ch.silviowangler.addresses.geocoder.domain;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author Silvio Wangler
 */
@Entity
@Table(name = "geo_location", uniqueConstraints={
        @UniqueConstraint(columnNames = {"street", "housenumber", "zip", "city", "country"})
}, indexes = {
		@Index(name="IDX_ZIP_LONG_LAT_PROC", columnList = "longitude,latitude,processed,zip"),
		@Index(name="IDX_LONG_LAT_PROC", columnList = "longitude,latitude,processed")
})
public class Location extends BaseEntity {

    @Column(nullable = false, length = 150)
    private String street;
    @Column(nullable = false, length = 10)
    private String housenumber;
    @Column(nullable = false, length = 10)
    private String zip;
    @Column(nullable = false, length = 50)
    private String city;
    @Column(nullable = false, length = 2)
    private String country;

    private Double longitude;
    private Double latitude;

    private Instant processed;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

	public Instant getProcessed() {
		return processed;
	}

	public void setProcessed(Instant processed) {
		this.processed = processed;
	}
}
