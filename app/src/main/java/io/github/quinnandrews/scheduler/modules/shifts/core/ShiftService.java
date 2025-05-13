package io.github.quinnandrews.scheduler.modules.shifts.core;

import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnCreate;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnUpdate;
import io.github.quinnandrews.scheduler.commons.exceptions.NotFoundException;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.Snapshot;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.SnapshotHistory;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Shift;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.repository.ClinicRepository;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.repository.EmployeeRepository;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.repository.ShiftRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final ClinicRepository clinicRepository;
    private final EmployeeRepository employeeRepository;

    public ShiftService(final ShiftRepository shiftRepository,
                        final ClinicRepository clinicRepository,
                        final EmployeeRepository employeeRepository) {
        this.shiftRepository = shiftRepository;
        this.clinicRepository = clinicRepository;
        this.employeeRepository = employeeRepository;
    }

    @Validated({OnCreate.class, Default.class})
    public Shift createShift(@Valid final Shift shift) {
        requireNonNullShift(shift);
        final var clinic = clinicRepository.findByIdWithCaching(shift.getClinicId());
        final var employee = employeeRepository.findByIdWithCaching(shift.getEmployeeId());
        return shiftRepository.save(
                shift.withClinic(clinic.orElse(null))
                        .withEmployee(employee.orElse(null))
        );
    }

    @Validated({OnUpdate.class, Default.class})
    public Shift updateShift(@Valid final Shift shift) {
        requireNonNullShift(shift);
        final var existing = getShiftOrElseThrow(shift.getId());
        return shiftRepository.save(existing.merge(shift));
    }

    @Transactional
    public Shift publishShift(final Long id) {
        requireNonNullId(id);
        final var shift = getShiftOrElseThrow(id);
        if (shift.isUnpublished()) {
            shiftRepository.saveAndFlush(shift.publish());
        }
        return shift;
    }

    @Transactional
    public Shift removeShift(final Long id) {
        requireNonNullId(id);
        final var shift = getShiftOrElseThrow(id);
        if (shift.isUnpublished() || shift.isPublished()) {
            return shiftRepository.save(shift.remove());
        }
        return shift;
    }

    public Shift getShiftOrElseThrow(final Long id) {
        requireNonNullId(id);
        return shiftRepository.findByIdWithCaching(id)
                .orElseThrow(() -> new NotFoundException(Shift.class, id));
    }

    public SnapshotHistory<Shift, Long> getShiftHistoryOrElseThrow(final Long id) {
        requireNonNullId(id);
        return shiftRepository.findSnapshotHistory(Shift.class, id)
                .orElseThrow(() -> new NotFoundException(
                        "Could not find History of " +  Shift.class.getSimpleName() + " with ID[" + id + "]."));
    }

    public Snapshot<Shift, Long> getShiftSnapshotOrElseThrow(final Long id,
                                                             final Integer version) {
        requireNonNullId(id);
        requireNonNullVersion(version);
        return shiftRepository.findSnapshot(Shift.class, id, version)
                .orElseThrow(() -> new NotFoundException(
                        "Could not find Snapshot of " +  Shift.class.getSimpleName() 
                                + " with ID[" + id + "] and VERSION[" + version +"]."));
    }

    private static void requireNonNullShift(final Shift shift) {
        Objects.requireNonNull(shift, "Argument 'shift' must not be null.");
    }

    private static void requireNonNullId(final Long id) {
        Objects.requireNonNull(id, "Argument 'id' must not be null.");
    }

    private static void requireNonNullVersion(final Integer version) {
        Objects.requireNonNull(version, "Argument 'version' must not be null.");
    }
}
