package io.github.quinnandrews.scheduler.config.data;

import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Optional;

import static io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.constants.CacheRegionConstants.ADMIN_SCHEDULE_LOCATION_CACHE_REGION;
import static io.github.quinnandrews.scheduler.modules.schedules.core.domain.constants.CacheRegionConstants.SCHEDULE_LOCATION_CACHE_REGION;

@EnableCaching
@Configuration
public class HazelcastCacheConfig {

    @Bean
    public CacheManager cacheManager(@Qualifier("hazelcastInstance")
                                     final HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public Config cacheConfig() {
        return new Config()
                .setInstanceName("hazelcast-cache")

                .addMapConfig(new CacheRegionConfig()
                        .withName(ADMIN_SCHEDULE_LOCATION_CACHE_REGION)
                        .withTimeToLive(Duration.ofHours(6))
                        .toMapConfig())

                .addMapConfig(new CacheRegionConfig()
                        .withName(SCHEDULE_LOCATION_CACHE_REGION)
                        .withTimeToLive(Duration.ofHours(6))
                        .toMapConfig())

                .addMapConfig(new CacheRegionConfig()
                        .withName("default-query-results-region")
                        .withTimeToLive(Duration.ofHours(6))
                        .withComment("""
                                Contains cached query results. \
                                
                                Entity values are never cached in a query cache. \
                                Only Entity IDs are cached. Entities and their values \
                                are cached in the Entity Cache Regions they belong \
                                to. \
                                
                                However, if the query returns a projection, then \
                                in that case the values are stored in the query cache \
                                as an Object Array. \
                                
                                NOTE: When Hazelcast is used as the Hibernate 2nd Level \
                                Cache Provider, Query Caches are always local and never \
                                distributed. \
                                
                                NOTE: If fine-grained control over a particular query or \
                                group of queries is needed, a new Cache Region can be \
                                defined for that case.""")
                        .toMapConfig())

                .addMapConfig(new CacheRegionConfig()
                        .withName("default-update-timestamps-region")
                        .withTimeToLive(Duration.ofHours(24))
                        .withComment("""
                                Contains timestamps of the most recent updates to \
                                queryable tables. These are used to validate the \
                                results as they are served from the query cache. \
                                
                                If a query result has been cached, but afterwards a \
                                table referenced in the query has been updated, then \
                                then the cached query results will be invalid and the \
                                next execution of the query will refresh the cache. \
                                
                                The TTL should always be higher than the TTL of Query \
                                Cache Region(s), and LRU should never be used as the \
                                Eviction Policy. \
                                
                                The TTL can be quite long for this region, even infinite, \
                                if desired.""")
                        .toMapConfig());
    }

    public static class CacheRegionConfig {

        private final MapConfig mapConfig = new MapConfig()
                .setPerEntryStatsEnabled(Boolean.TRUE);

        private final EvictionConfig evictionConfig = new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.NONE)
                .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
                .setSize(EvictionConfig.DEFAULT_MAX_ENTRY_COUNT);

        private String comment;

        public CacheRegionConfig() {
            // no-op
        }

        public String getComment() {
            return comment;
        }

        public CacheRegionConfig withName(final String name) {
            mapConfig.setName(name);
            return this;
        }

        public CacheRegionConfig withTimeToLive(final Duration duration) {
            Optional.ofNullable(duration).ifPresentOrElse(
                    d -> mapConfig.setTimeToLiveSeconds(Long.valueOf(d.toSeconds()).intValue()),
                    () -> mapConfig.setTimeToLiveSeconds(MapConfig.DEFAULT_TTL_SECONDS));
            return this;
        }

        public CacheRegionConfig withMaxSizePolicy(final MaxSizePolicy maxSizePolicy) {
            Optional.ofNullable(maxSizePolicy).ifPresentOrElse(
                    d -> evictionConfig.setMaxSizePolicy(maxSizePolicy),
                    () -> evictionConfig.setMaxSizePolicy(MaxSizePolicy.PER_NODE));
            return this;
        }

        public CacheRegionConfig withMaxSize(final Integer maxSize) {
            Optional.ofNullable(maxSize).ifPresentOrElse(
                    d -> evictionConfig.setSize(maxSize),
                    () -> evictionConfig.setSize(EvictionConfig.DEFAULT_MAX_ENTRY_COUNT));
            return this;
        }

        public CacheRegionConfig withComment(final String comment) {
            this.comment = comment;
            return this;
        }

        public MapConfig toMapConfig() {
            return mapConfig.setEvictionConfig(evictionConfig);
        }
    }
}
