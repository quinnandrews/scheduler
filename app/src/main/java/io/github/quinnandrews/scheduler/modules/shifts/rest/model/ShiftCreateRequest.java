package io.github.quinnandrews.scheduler.modules.shifts.rest.model;

import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Shift;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShiftCreateRequest(
        Long clinicId,
        Long employeeId,
        LocalDate localDateStart,
        LocalTime localTimeStart,
        LocalDate localDateEnd,
        LocalTime localTimeEnd
) {

    public Shift toShift() {
        return new Shift()
                .withClinicId(clinicId)
                .withEmployeeId(employeeId)
                .withLocalDateStart(localDateStart)
                .withLocalTimeStart(localTimeStart)
                .withLocalDateEnd(localDateEnd)
                .withLocalTimeEnd(localTimeEnd);
    }
}
