package io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.VersioningEntity;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnCreate;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnUpdate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;
import org.javers.core.metamodel.annotation.TypeName;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.*;
import static io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location.TABLE;
import static io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.constants.CacheRegionConstants.ADMIN_SCHEDULE_LOCATION_CACHE_REGION;
import static io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.constants.TypeConstants.ADMIN_SCHEDULE_LOCATION_TYPE;

@Entity
@TypeName(ADMIN_SCHEDULE_LOCATION_TYPE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(region = ADMIN_SCHEDULE_LOCATION_CACHE_REGION,
        usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = TABLE)
public class Location implements VersioningEntity {

    public static final String TABLE = "location";
    public static final String SEQUENCE = TABLE + ID_SEQUENCE_SUFFIX;
    public static final String SEQUENCE_GENERATOR = SEQUENCE + SEQUENCE_GENERATOR_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = SEQUENCE_GENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENERATOR,
            sequenceName = SEQUENCE,
            allocationSize = 1)
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @Column(name = "id",
            columnDefinition = SERIAL,
            nullable = false,
            insertable = false,
            updatable = false)
    private Integer id;

    @Version
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @PositiveOrZero
    @Column(name = "version",
            columnDefinition = INT,
            nullable = false)
    private Integer version;

    @CreatedDate
    @Column(name = "date_created",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false,
            updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_last_modified",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false)
    private LocalDateTime dateLastModified;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_code",
            columnDefinition = VARCHAR_16,
            nullable = false)
    private Status status;

    @NotBlank
    @Size(max = 32)
    @Column(name = "name",
            columnDefinition = VARCHAR_32,
            nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(name = "state",
            columnDefinition = VARCHAR_2,
            nullable = false)
    private String state;

    @NotBlank
    @Size(max = 64)
    @Column(name = "time_zone",
            columnDefinition = VARCHAR_64,
            nullable = false)
    private String timeZone;

    @NotNull
    @Range(min = -90, max = 90)
    @Column(name = "latitude",
            columnDefinition = DOUBLE_PRECISION,
            nullable = false)
    private Double latitude;

    @NotNull
    @Range(min = -180, max = 180)
    @Column(name = "longitude",
            columnDefinition = DOUBLE_PRECISION,
            nullable = false)
    private Double longitude;

    @NotNull
    @Range(min = 1, max = 60)
    @Column(name = "radius",
            columnDefinition = INT,
            nullable = false)
    private Integer radius;

    public Location() {
        // no-op
    }

    // -------------------------------------------- GETTERS

    public Integer getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }

    public Status getStatus() {
        return status;
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

    // -------------------------------------------- FLUENT-API

    public Location withId(final Integer id) {
        this.id = id;
        return this;
    }

    public Location withVersion(final Integer version) {
        this.version = version;
        return this;
    }

    public Location withStatus(final Status status) {
        this.status = status;
        return this;
    }

    public Location withName(final String name) {
        this.name = name;
        return this;
    }

    public Location withState(final String state) {
        this.state = state;
        return this;
    }

    public Location withTimeZone(final String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public Location withLatitude(final Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Location withLongitude(final Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Location withRadius(final Integer radius) {
        this.radius = radius;
        return this;
    }

    // -------------------------------------------- BEHAVIOR METHODS

    public boolean isActive() {
        return Status.ACTIVE.equals(getStatus());
    }

    public Location merge(final Location other) {
        if (this.equals(other)) {
            return withVersion(other.getVersion())
                    .withName(other.getName())
                    .withState(other.getState())
                    .withTimeZone(other.getTimeZone())
                    .withLatitude(other.getLatitude())
                    .withLongitude(other.getLongitude())
                    .withRadius(other.getRadius())
                    .withStatus(other.getStatus());
        }
        throw new IllegalArgumentException("Argument 'other' must equal this instance.");
    }

    // -------------------------------------------- ENUMS

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    // -------------------------------------------- OBJECT-METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Location location)) return false;
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
                .append("version", getVersion())
                .append("dateCreated", getDateCreated())
                .append("dateLastModified", getDateLastModified())
                .append("status", getStatus())
                .append("name", getName())
                .append("state", getState())
                .append("timeZone", getTimeZone())
                .append("latitude", getLatitude())
                .append("longitude", getLongitude())
                .append("radius", getRadius())
                .toString();
    }
}
