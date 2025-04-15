package io.github.quinnandrews.scheduler.commons.core.domain.entities;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.AuthorSummary;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Immutable;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.VARCHAR_255;
import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.VARCHAR_32;

/**
 * Represents...
 */
@Immutable
@MappedSuperclass
public abstract class EnumeratedEntity<C> {

    @Id
    @Column(name = "code",
            columnDefinition = VARCHAR_32,
            nullable = false,
            insertable = false,
            updatable = false)
    @Enumerated(EnumType.STRING)
    private C code;

    @Column(name = "name",
            columnDefinition = VARCHAR_32,
            nullable = false,
            insertable = false,
            updatable = false)
    private String name;

    @Column(name = "description",
            columnDefinition = VARCHAR_255,
            nullable = false,
            insertable = false,
            updatable = false)
    private String description;

    @Embedded
    private AuthorSummary authorSummary;

    protected EnumeratedEntity() {
        // no-op
    }

    protected EnumeratedEntity(final C code) {
        this.code = code;
    }

    // -------------------------------------------- GETTERS

    public C getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AuthorSummary getAuthorSummary() {
        return authorSummary;
    }

    // -------------------------------------------- OBJECT METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final EnumeratedEntity<?> that)) return false;
        return new EqualsBuilder()
                .append(getCode(), that.getCode())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCode())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("code", getCode())
                .append("name", getName())
                .append("description", getDescription())
                .append("authorSummary", getAuthorSummary())
                .toString();
    }
}
