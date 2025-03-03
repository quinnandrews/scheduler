package io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.repositories;

import io.github.quinnandrews.scheduler.commons.core.domain.repositories.CachingJpaRepository;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.repositories.SnapshotRepository;
import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@JaversSpringDataAuditable
@Repository
public interface LocationRepository extends CachingJpaRepository<Location, Integer>,
                                            SnapshotRepository<Location, Integer> {
}
