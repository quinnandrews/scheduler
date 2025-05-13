package io.github.quinnandrews.scheduler.modules.shifts.core.domain;

import io.github.quinnandrews.scheduler.commons.core.domain.entities.EnumeratedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "shift_status")
public class ShiftStatus extends EnumeratedEntity<ShiftStatus.Code> {

    public ShiftStatus() {
        super();
    }

    public ShiftStatus(final ShiftStatus.Code code) {
        super(code);
    }

    public enum Code {
        DRAFT,
        PUBLISHED,
        UNPUBLISHED,
        REMOVED
    }
}
