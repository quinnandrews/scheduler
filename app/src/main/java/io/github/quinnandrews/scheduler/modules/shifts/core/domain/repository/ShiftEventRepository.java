package io.github.quinnandrews.scheduler.modules.shifts.core.domain.repository;

import io.github.quinnandrews.scheduler.commons.core.domain.repositories.SpecificationJpaRepository;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.ShiftEvent;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftEventRepository extends SpecificationJpaRepository<ShiftEvent, Long> {
}
