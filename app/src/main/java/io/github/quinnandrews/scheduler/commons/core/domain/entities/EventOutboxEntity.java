package io.github.quinnandrews.scheduler.commons.core.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.*;

@MappedSuperclass
public abstract class EventOutboxEntity<S extends EventOutboxEntity<S, T, E>, T, E> {

    @Transient
    private E instance;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "event_outbox_id_seq_gen")
    @SequenceGenerator(name = "event_outbox_id_seq_gen",
            sequenceName = "event_outbox_id_seq",
            allocationSize = 1)
    @Column(name = "id",
            columnDefinition = BIG_SERIAL,
            nullable = false,
            insertable = false,
            updatable = false)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_code",
            columnDefinition = VARCHAR_32,
            nullable = false,
            updatable = false)
    private T typeCode;

    @NotNull
    @Column(name = "date_occurred",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            nullable = false,
            updatable = false)
    private Instant dateOccurred;

    @NotNull
    @Column(name = "message_produced",
            columnDefinition = BOOLEAN,
            nullable = false)
    private Boolean messageProduced;

    @NotNull
    @Column(name = "message_attempts",
            columnDefinition = INT,
            nullable = false)
    private Integer messageAttempts;

    @Column(name = "date_last_attempted",
            columnDefinition = TIMESTAMP_WITH_TIME_ZONE,
            insertable = false)
    private Instant dateLastAttempted;

    protected EventOutboxEntity() {
        dateOccurred = Instant.now();
        messageProduced = Boolean.FALSE;
        messageAttempts = 0;
    }

    // -------------------------------------------- GETTERS

    protected E getInstance() {
        return instance;
    }

    public Long getId() {
        return id;
    }

    public T getTypeCode() {
        return typeCode;
    }

    public Instant getDateOccurred() {
        return dateOccurred;
    }

    public Boolean getMessageProduced() {
        return messageProduced;
    }

    public Integer getMessageAttempts() {
        return messageAttempts;
    }

    public Instant getDateLastAttempted() {
        return dateLastAttempted;
    }

    // -------------------------------------------- FLUENT API

    public S withInstance(final E instance) {
        this.instance = instance;
        return self();
    }

    public S withTypeCode(final T typeCode) {
        this.typeCode = typeCode;
        return self();
    }

    @SuppressWarnings("unchecked")
    protected S self() {
        return (S) this;
    }

    // -------------------------------------------- BEHAVIOR METHODS

    public S messageAttempted() {
        messageAttempts++;
        dateLastAttempted = Instant.now();
        return self();
    }

    public S messageProduced() {
        messageProduced = Boolean.TRUE;
        return self();
    }
}
