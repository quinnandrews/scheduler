package io.github.quinnandrews.scheduler.modules.shifts.core.domain;

import io.github.quinnandrews.scheduler.commons.core.domain.entities.EnumeratedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "shift_event_type")
public class ShiftEventType extends EnumeratedEntity<ShiftEventType.Code> {

    public ShiftEventType() {
        super();
    }

    public ShiftEventType(final ShiftEventType.Code code) {
        super(code);
    }

    public enum Code {
        PUBLICATION,
        REMOVAL
    }
}
