package io.github.quinnandrews.scheduler.modules.shifts.core.domain.repository;

import io.github.quinnandrews.scheduler.commons.core.domain.repositories.CachingJpaRepository;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.repositories.SnapshotRepository;
import io.github.quinnandrews.scheduler.modules.shifts.core.domain.Shift;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository
public interface ShiftRepository extends CachingJpaRepository<Shift, Long>,
                                         SnapshotRepository<Shift, Long> {
}
