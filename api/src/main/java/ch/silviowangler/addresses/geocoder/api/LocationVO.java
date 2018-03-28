package ch.silviowangler.addresses.geocoder.api;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Silvio Wangler
 */
public class LocationVO implements Serializable {

    private UUID id;
    private String street;
    private String housenumber;
    private String zip;
    private String city;
    private String country;
    private Double longitude;
    private Double latitude;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationVO that = (LocationVO) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getStreet(), that.getStreet()) &&
                Objects.equals(getHousenumber(), that.getHousenumber()) &&
                Objects.equals(getZip(), that.getZip()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getLongitude(), that.getLongitude()) &&
                Objects.equals(getLatitude(), that.getLatitude());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getStreet(), getHousenumber(), getZip(), getCity(), getCountry(), getLongitude(), getLatitude());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LocationVO{");
        sb.append("id=").append(id);
        sb.append(", street='").append(street).append('\'');
        sb.append(", housenumber='").append(housenumber).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append('}');
        return sb.toString();
    }
}
