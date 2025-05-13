package io.github.quinnandrews.scheduler.modules.shifts.core.domain;

import io.github.quinnandrews.scheduler.commons.core.domain.caching.ReadWriteCacheRegion;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnCreate;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnUpdate;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.AuthorSummary;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.VersionedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javers.core.metamodel.annotation.TypeName;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.*;
import static io.github.quinnandrews.scheduler.modules.shifts.core.domain.constants.TypeConstants.SHIFT_TYPE;

@Entity
@Table(name = "shift")
@EntityListeners(AuditingEntityListener.class)
@Cacheable
@ReadWriteCacheRegion(SHIFT_TYPE)
@TypeName(SHIFT_TYPE)
public class Shift extends AbstractAggregateRoot<Shift> implements VersionedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "shift_id_seq_gen")
    @SequenceGenerator(name = "shift_id_seq_gen",
            sequenceName = "shift_id_seq",
            allocationSize = 1)
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @Column(name = "id",
            columnDefinition = BIG_SERIAL,
            nullable = false,
            insertable = false,
            updatable = false)
    private Long id;

    @Version
    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @PositiveOrZero
    @Column(name = "version",
            columnDefinition = INT,
            nullable = false)
    private Integer version;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status_code",
            columnDefinition = VARCHAR_32,
            nullable = false)
    private ShiftStatus.Code statusCode;

    @NotNull
    @Column(name = "clinic_id",
            columnDefinition = BIG_INT,
            nullable = false,
            insertable = false,
            updatable = false)
    private Long clinicId;

    @ManyToOne
    @JoinColumn(name = "clinic_id",
                columnDefinition = BIG_INT,
                nullable = false,
                updatable = false)
    private Clinic clinic;

    @NotNull
    @Column(name = "employee_id",
            columnDefinition = BIG_INT,
            nullable = false,
            insertable = false,
            updatable = false)
    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "employee_id",
            columnDefinition = BIG_INT,
            nullable = false,
            updatable = false)
    private Employee employee;

    @NotNull
    @Column(name = "local_date_start",
            columnDefinition = DATE,
            nullable = false)
    private LocalDate localDateStart;

    @NotNull
    @Column(name = "local_time_start",
            columnDefinition = TIME,
            nullable = false)
    private LocalTime localTimeStart;

    @NotNull
    @Column(name = "local_date_end",
            columnDefinition = DATE,
            nullable = false)
    private LocalDate localDateEnd;

    @NotNull
    @Column(name = "local_time_end",
            columnDefinition = TIME,
            nullable = false)
    private LocalTime localTimeEnd;

    @Embedded
    private AuthorSummary authorSummary;

    public Shift() {
        statusCode = ShiftStatus.Code.DRAFT;
        authorSummary = new AuthorSummary();
    }

    // -------------------------------------------- GETTERS

    public Long getId() {
        return id;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    public ShiftStatus.Code getStatusCode() {
        return statusCode;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getLocalDateStart() {
        return localDateStart;
    }

    public LocalTime getLocalTimeStart() {
        return localTimeStart;
    }

    public LocalDate getLocalDateEnd() {
        return localDateEnd;
    }

    public LocalTime getLocalTimeEnd() {
        return localTimeEnd;
    }

    public AuthorSummary getAuthorSummary() {
        return authorSummary;
    }

    // -------------------------------------------- FLUENT API

    public Shift withId(final Long id) {
        this.id = id;
        return this;
    }

    public Shift withVersion(final Integer version) {
        this.version = version;
        return this;
    }

    public Shift withClinicId(final Long clinicId) {
        this.clinicId = clinicId;
        return this;
    }

    public Shift withClinic(final Clinic clinic) {
        this.clinic = clinic;
        return this;
    }

    public Shift withEmployeeId(final Long employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public Shift withEmployee(final Employee employee) {
        this.employee = employee;
        return this;
    }

    public Shift withLocalDateStart(final LocalDate localDateStart) {
        this.localDateStart = localDateStart;
        return this;
    }

    public Shift withLocalTimeStart(final LocalTime localTimeStart) {
        this.localTimeStart = localTimeStart;
        return this;
    }

    public Shift withLocalDateEnd(final LocalDate localDateEnd) {
        this.localDateEnd = localDateEnd;
        return this;
    }

    public Shift withLocalTimeEnd(final LocalTime localTimeEnd) {
        this.localTimeEnd = localTimeEnd;
        return this;
    }

    // -------------------------------------------- BEHAVIOR METHODS

    public Shift merge(final Shift other) {
        final var hasChanges = !(new EqualsBuilder()
                .append(getLocalDateStart(), other.getLocalTimeStart())
                .append(getLocalDateEnd(), other.getLocalDateEnd())
                .append(getLocalTimeEnd(), other.getLocalTimeEnd())
                .isEquals());
        if (hasChanges) {
            return this.withLocalTimeStart(other.getLocalTimeStart())
                    .withLocalDateEnd(other.getLocalDateEnd())
                    .withLocalTimeEnd(other.getLocalTimeEnd())
                    .unpublish();
        }
        return this;
    }

    public boolean isDraft() {
        return ShiftStatus.Code.DRAFT.equals(statusCode);
    }

    public boolean isUnpublished() {
        return ShiftStatus.Code.UNPUBLISHED.equals(statusCode);
    }

    public boolean isPublished() {
        return ShiftStatus.Code.PUBLISHED.equals(statusCode);
    }

    public boolean isRemoved() {
        return ShiftStatus.Code.REMOVED.equals(statusCode);
    }

    private Shift unpublish() {
        if (isUnpublished()) {
            return this;
        }
        if (isRemoved()) {
            throw new IllegalStateException(
                    "Cannot unpublish Shift when it has already been removed.");
        }
        if (isPublished() || isDraft()) {
            statusCode = ShiftStatus.Code.UNPUBLISHED;
            return this;
        }
        throw new IllegalStateException("Cannot unpublish Shift.");
    }

    public Shift publish() {
        if (isPublished()) {
            return this;
        }
        if (isDraft()) {
            throw new IllegalStateException(
                    "Cannot publish Shift when it has not yet been persisted.");
        }
        if (isRemoved()) {
            throw new IllegalStateException(
                    "Cannot publish Shift when it has already been removed.");
        }
        if (isUnpublished()) {
            statusCode = ShiftStatus.Code.PUBLISHED;
            final var event = new ShiftEvent()
                    .withInstance(this)
                    .withTypeCode(ShiftEventType.Code.PUBLICATION);
            return andEvent(event);
        }
        throw new IllegalStateException("Cannot publish Shift.");
    }

    public Shift remove() {
        if (isRemoved()) {
            return this;
        }
        if (isDraft()) {
            throw new IllegalStateException(
                    "Cannot remove Shift when it has not yet been persisted.");
        }
        if (isUnpublished() || isPublished()) {
            statusCode = ShiftStatus.Code.REMOVED;
            final var event = new ShiftEvent()
                    .withInstance(this)
                    .withTypeCode(ShiftEventType.Code.REMOVAL);
            return andEvent(event);
        }
        throw new IllegalStateException("Cannot remove Shift.");
    }

    // -------------------------------------------- LIFECYCLE METHODS

    @PrePersist
    public void prePersist() {
        unpublish();
    }

    @PreUpdate
    public void preUpdate() {
        unpublish();
    }

    // -------------------------------------------- OBJECT METHODS

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Shift shift)) return false;
        return new EqualsBuilder()
                .append(getId(), shift.getId())
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
                .append("version", getVersion())
                .append("statusCode", getStatusCode())
                .append("clinicId", getClinicId())
                .append("employeeId", getEmployeeId())
                .append("localDateStart", getLocalDateStart())
                .append("localTimeStart", getLocalTimeStart())
                .append("localDateEnd", getLocalDateEnd())
                .append("localTimeEnd", getLocalTimeEnd())
                .append("authorSummary", getAuthorSummary())
                .append("clinic", getClinic())
                .append("employee", getEmployee())
                .toString();
    }
}
