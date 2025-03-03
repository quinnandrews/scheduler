package io.github.quinnandrews.scheduler.modules.schedules.core.domain.repositories;

import io.github.quinnandrews.scheduler.commons.core.domain.repositories.CachingJpaRepository;
import io.github.quinnandrews.scheduler.modules.schedules.core.domain.LocationOption;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationOptionRepository extends CachingJpaRepository<LocationOption, Integer> {
}
