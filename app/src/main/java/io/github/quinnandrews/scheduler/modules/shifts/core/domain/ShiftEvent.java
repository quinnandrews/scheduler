package io.github.quinnandrews.scheduler.modules.shifts.core.domain;

import io.github.quinnandrews.scheduler.commons.core.domain.entities.EventOutboxEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.BIG_INT;
import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.INT;

@Entity
@Table(name = "shift_event")
public class ShiftEvent extends EventOutboxEntity<ShiftEvent, ShiftEventType.Code, Shift> {

    @NotNull
    @Column(name = "shift_id",
            columnDefinition = BIG_INT,
            nullable = false,
            updatable = false)
    private Long shiftId;

    @NotNull
    @Column(name = "shift_version",
            columnDefinition = INT,
            nullable = false,
            updatable = false)
    private Integer shiftVersion;

    public ShiftEvent() {
        super();
    }

    // -------------------------------------------- GETTERS

    public Long getShiftId() {
        return shiftId;
    }

    public Integer getShiftVersion() {
        return shiftVersion;
    }

    // -------------------------------------------- BEHAVIOR METHODS

    public boolean isPublication() {
        return ShiftEventType.Code.PUBLICATION.equals(getTypeCode());
    }

    public boolean isRemoval() {
        return ShiftEventType.Code.REMOVAL.equals(getTypeCode());
    }

    // -------------------------------------------- LIFECYCLE METHODS

    @PrePersist
    public void prePersist() {
        shiftId = getInstance().getId();
        shiftVersion = getInstance().getVersion();
    }

    // -------------------------------------------- OBJECT METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final ShiftEvent that)) return false;
        return new EqualsBuilder()
                .append(getId(), that.getId())
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
                .append("shiftId", getShiftId())
                .append("shiftVersion", getShiftVersion())
                .append("typeCode", getTypeCode())
                .append("dateOccurred", getDateOccurred())
                .append("messageProduced", getMessageProduced())
                .append("messageAttempts", getMessageAttempts())
                .append("dateLastAttempted", getDateLastAttempted())
                .toString();
    }
}
