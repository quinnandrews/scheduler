package io.github.quinnandrews.scheduler.commons.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.TIMESTAMP_WITH_TIME_ZONE;
import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.VARCHAR_32;

@MappedSuperclass
public abstract class EventOutboxEntity<E extends EventOutboxEntity<E, K, T>, K, T> {

    @NotNull
    @EmbeddedId
    public K key;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type",
            columnDefinition = VARCHAR_32,
            nullable = false)
    private T type;

    @Column(name = "date_occurred",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false,
            updatable = false)
    private LocalDateTime dateOccurred;

    @Column(name = "date_message_produced",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            insertable = false)
    private LocalDateTime dateMessageProduced;

    protected EventOutboxEntity() {
        // no-op
    }

    // -------------------------------------------- GETTERS

    public K getKey() {
        return key;
    }

    public T getType() {
        return type;
    }

    public LocalDateTime getDateOccurred() {
        return dateOccurred;
    }

    public LocalDateTime getDateMessageProduced() {
        return dateMessageProduced;
    }

    // -------------------------------------------- FLUENT API

    public E withKey(final K key) {
        this.key = key;
        return self();
    }

    public E withType(final T type) {
        this.type = type;
        return self();
    }

    @SuppressWarnings("unchecked")
    protected E self() {
        return (E) this;
    }

    // -------------------------------------------- BEHAVIOR METHODS

    public E messageAttempted() {
        // increment field
        return self();
    }

    public E messageProduced() {
        dateMessageProduced = ZonedDateTime.now(ZoneId.of("UTC"))
                .toLocalDateTime();
        return self();
    }

    // -------------------------------------------- LIFECYCLE METHODS

    @PrePersist
    public void prePersist() {
        dateOccurred = ZonedDateTime.now(ZoneId.of("UTC"))
                .toLocalDateTime();
    }

    // -------------------------------------------- OBJECT METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final EventOutboxEntity<?, ?, ?> that)) return false;
        return new EqualsBuilder()
                .append(getKey(), that.getKey())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getKey())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("key", getKey())
                .append("type", getType())
                .append("dateOccurred", getDateOccurred())
                .append("dateMessageProduced", getDateMessageProduced())
                .toString();
    }
}
