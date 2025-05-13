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
import static io.github.quinnandrews.scheduler.modules.shifts.core.domain.constants.TypeConstants.SHIFT_EMPLOYEE_TYPE;

@Immutable
@Entity
@Table(name = "employee")
@Cacheable
@ReadOnlyCacheRegion(SHIFT_EMPLOYEE_TYPE)
@TypeName(SHIFT_EMPLOYEE_TYPE)
public class Employee {

    @Id
    @Column(name = "id",
            columnDefinition = BIG_SERIAL,
            nullable = false,
            insertable = false,
            updatable = false)
    private Long id;

    @Column(name = "first_name",
            columnDefinition = VARCHAR_64,
            nullable = false,
            insertable = false,
            updatable = false)
    private String firstName;

    @Column(name = "last_name",
            columnDefinition = VARCHAR_64,
            nullable = false,
            insertable = false,
            updatable = false)
    private String lastName;

    @Column(name = "credentials",
            columnDefinition = VARCHAR_32,
            nullable = false,
            insertable = false,
            updatable = false)
    private String credentials;

    public Employee() {
        // no-op
    }

    // -------------------------------------------- GETTERS

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCredentials() {
        return credentials;
    }

    // -------------------------------------------- OBJECT METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Employee employee)) return false;
        return new EqualsBuilder()
                .append(getId(), employee.getId())
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
                .append("firstName", getFirstName())
                .append("lastName", getLastName())
                .append("credentials", getCredentials())
                .toString();
    }
}
