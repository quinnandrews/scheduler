package io.github.quinnandrews.scheduler.modules.shifts.rest.model;

import io.github.quinnandrews.scheduler.commons.snapshots.rest.model.AuthorSummaryResponse;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Shift;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftStatus;

import java.time.*;

public record ShiftResponse (
        Long id,
        Integer version,
        ShiftStatus.Code status,
        Long clinicId,
        Long employeeId,
        LocalDate localDateStart,
        LocalTime localTimeStart,
        LocalDate localDateEnd,
        LocalTime localTimeEnd,
        AuthorSummaryResponse _authorSummary
) {

    public static ShiftResponse of(final Shift shift) {
        return new ShiftResponse(
                shift.getId(),
                shift.getVersion(),
                shift.getStatusCode(),
                shift.getClinicId(),
                shift.getEmployeeId(),
                shift.getLocalDateStart(),
                shift.getLocalTimeStart(),
                shift.getLocalDateEnd(),
                shift.getLocalTimeEnd(),
                AuthorSummaryResponse.of(shift.getAuthorSummary())
        );
    }
}
