package io.github.quinnandrews.scheduler.modules.schedules.core.domain.constants;

import static io.github.quinnandrews.scheduler.commons.core.domain.constants.JPAEntityConstants.ENTITY_CACHE_REGION_PREFIX;
import static io.github.quinnandrews.scheduler.modules.schedules.core.domain.constants.TypeConstants.SCHEDULE_LOCATION_TYPE;

public class CacheRegionConstants {

    public static final String SCHEDULE_LOCATION_CACHE_REGION = ENTITY_CACHE_REGION_PREFIX + SCHEDULE_LOCATION_TYPE;
}
