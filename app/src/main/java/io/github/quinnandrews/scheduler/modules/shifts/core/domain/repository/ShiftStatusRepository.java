package io.github.quinnandrews.scheduler.modules.shifts.core.domain.repository;

import io.github.quinnandrews.scheduler.commons.core.domain.repositories.CachingJpaRepository;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftStatusRepository extends CachingJpaRepository<ShiftStatus, ShiftStatus.Code> {
}
