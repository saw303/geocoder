package ch.silviowangler.addresses.geocoder.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {

    /**
     * Unique identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(unique = true, name = "external_key", nullable = false, length = 36)
    private String uuid = UUID.randomUUID().toString();

    @Version
    @Column(nullable = false)
    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    /**
     * Make sure it is a valid UUID.
     *
     * @param uuid UUID as String.
     */
    public void setUuid(String uuid) {
        this.uuid = UUID.fromString(uuid).toString();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid);
    }

    @Override
    public String toString() {
        return String.format("%s (id=%s, uuid=%s, version=%n)",
                getClass().getSimpleName(),
                this.id.toString(),
                this.uuid,
                this.version);
    }
}
