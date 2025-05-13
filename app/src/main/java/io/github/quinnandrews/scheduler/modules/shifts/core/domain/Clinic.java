package io.github.quinnandrews.scheduler.modules.shifts.core.domain;

import io.github.quinnandrews.scheduler.commons.core.domain.caching.ReadOnlyCacheRegion;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Immutable;
import org.javers.core.metamodel.annotation.TypeName;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.*;
import static io.github.quinnandrews.scheduler.modules.shifts.core.domain.constants.TypeConstants.SHIFT_CLINIC_TYPE;

@Immutable
@Entity
@Table(name = "clinic")
@Cacheable
@ReadOnlyCacheRegion(SHIFT_CLINIC_TYPE)
@TypeName(SHIFT_CLINIC_TYPE)
public class Clinic {

    @Id
    @Column(name = "id",
            columnDefinition = BIG_SERIAL,
            nullable = false,
            insertable = false,
            updatable = false)
    private Long id;

    @Column(name = "name",
            columnDefinition = VARCHAR_32,
            nullable = false,
            insertable = false,
            updatable = false)
    private String name;

    @Column(name = "city",
            columnDefinition = VARCHAR_32,
            nullable = false,
            insertable = false,
            updatable = false)
    private String city;

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

    public Clinic() {
        // no-op
    }

    // -------------------------------------------- GETTERS

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getTimeZone() {
        return timeZone;
    }

    // -------------------------------------------- OBJECT METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Clinic clinic)) return false;
        return new EqualsBuilder()
                .append(getId(), clinic.getId())
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
                .append("city", getCity())
                .append("state", getState())
                .append("timeZone", getTimeZone())
                .toString();
    }
}
