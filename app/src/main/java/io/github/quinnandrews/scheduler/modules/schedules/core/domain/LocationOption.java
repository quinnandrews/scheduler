package io.github.quinnandrews.scheduler.modules.schedules.core.domain;

import io.github.quinnandrews.scheduler.commons.core.domain.caching.ReadOnlyCacheRegion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Immutable;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.*;
import static io.github.quinnandrews.scheduler.modules.schedules.core.domain.constants.CacheRegionConstants.SCHEDULE_LOCATION_CACHE_REGION;

@Immutable
@Entity
@ReadOnlyCacheRegion(region = SCHEDULE_LOCATION_CACHE_REGION)
@Table(name = "location")
public class LocationOption {

    @Id
    @Column(name = "id",
            columnDefinition = SERIAL,
            nullable = false,
            insertable = false,
            updatable = false)
    private Integer id;

    @Column(name = "name",
            columnDefinition = VARCHAR_32,
            nullable = false,
            insertable = false,
            updatable = false)
    private String name;

    @Column(name = "state",
            columnDefinition = VARCHAR_2,
            nullable = false,
            insertable = false,
            updatable = false)
    private String state;

    @Column(name = "time_zone",
            columnDefinition = VARCHAR_64,
            nullable = false,
            insertable = false,
            updatable = false)
    private String timeZone;

    @Column(name = "latitude",
            columnDefinition = DOUBLE_PRECISION,
            nullable = false,
            insertable = false,
            updatable = false)
    private Double latitude;

    @Column(name = "longitude",
            columnDefinition = DOUBLE_PRECISION,
            nullable = false,
            insertable = false,
            updatable = false)
    private Double longitude;

    @Column(name = "radius",
            columnDefinition = INT,
            nullable = false,
            insertable = false,
            updatable = false)
    private Integer radius;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_code",
            columnDefinition = VARCHAR_16,
            nullable = false,
            insertable = false,
            updatable = false)
    private Status status;

    public LocationOption() {
        // no-op
    }

    // -------------------------------------------- GETTERS

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getRadius() {
        return radius;
    }

    public Status getStatus() {
        return status;
    }

    // -------------------------------------------- BEHAVIOR METHODS

    public boolean isActive() {
        return Status.ACTIVE.equals(getStatus());
    }

    // -------------------------------------------- ENUMS

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    // -------------------------------------------- OBJECT METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final LocationOption location)) return false;
        return new EqualsBuilder()
                .append(getId(), location.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("state", getState())
                .append("timeZone", getTimeZone())
                .append("latitude", getLatitude())
                .append("longitude", getLongitude())
                .append("radius", getRadius())
                .append("status", getStatus())
                .toString();
    }
}
