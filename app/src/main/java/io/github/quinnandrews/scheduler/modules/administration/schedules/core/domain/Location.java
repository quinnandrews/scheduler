package io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain;

import io.github.quinnandrews.scheduler.commons.core.domain.caching.ReadWriteCacheRegion;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.AuthorSummary;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnCreate;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnUpdate;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.VersionedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Range;
import org.javers.core.metamodel.annotation.TypeName;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.*;
import static io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location.TABLE;
import static io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.constants.TypeConstants.ADMIN_SCHEDULE_LOCATION_TYPE;

@Entity
@Table(name = TABLE)
@EntityListeners(AuditingEntityListener.class)
@Cacheable
@ReadWriteCacheRegion(ADMIN_SCHEDULE_LOCATION_TYPE)
@TypeName(ADMIN_SCHEDULE_LOCATION_TYPE)
public class Location implements VersionedEntity {

    public static final String TABLE = "location";
    public static final String SEQUENCE = TABLE + ID_SEQUENCE_SUFFIX;
    public static final String SEQUENCE_GENERATOR = SEQUENCE + SEQUENCE_GENERATOR_SUFFIX;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = SEQUENCE_GENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENERATOR,
            sequenceName = SEQUENCE,
            allocationSize = 1)
    @Column(name = "id",
            columnDefinition = SERIAL,
            nullable = false,
            insertable = false,
            updatable = false)
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @Version
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @PositiveOrZero
    @Column(name = "version",
            columnDefinition = INT,
            nullable = false)
    private Integer version;

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

    @Embedded
    private AuthorSummary authorSummary;

    public Location() {
        // no-op
    }

    // -------------------------------------------- GETTERS

    public @Null(groups = OnCreate.class) @NotNull(groups = OnUpdate.class) Long getId() {
        return id;
    }

    @Override
    public Integer getVersion() {
        return version;
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

    public AuthorSummary getAuthorSummary() {
        return authorSummary;
    }

    // -------------------------------------------- FLUENT-API

    public Location withId(final @Null(groups = OnCreate.class) @NotNull(groups = OnUpdate.class) Long id) {
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
                .append("status", getStatus())
                .append("name", getName())
                .append("state", getState())
                .append("timeZone", getTimeZone())
                .append("latitude", getLatitude())
                .append("longitude", getLongitude())
                .append("radius", getRadius())
                .append("authorSummary", getAuthorSummary())
                .toString();
    }
}
