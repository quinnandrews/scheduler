package io.github.quinnandrews.scheduler.config.data;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.VersionedEntity;
import org.javers.spring.auditable.AuthorProvider;
import org.javers.spring.auditable.CommitPropertiesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Map;

@Configuration
public class JaversSnapshotConfig {

    @Bean
    public CommitPropertiesProvider commitPropertiesProvider() {
        return new CommitPropertiesProvider() {
            @Override
            public Map<String, String> provideForCommittedObject(final Object domainObject) {
                if (domainObject instanceof final VersionedEntity entity) {
                    return Map.of(
                            VersionedEntity.VERSION_KEY, entity.getVersion().toString()
                    );
                }
                return Collections.emptyMap();
            }
        };
    }

    @Bean
    public AuthorProvider authorProvider() {
        return () -> "qandrews@example.com";

        // SpringSecurityAuthorProvider is provided by Javers as
        // a default integration with Spring Security.
        // return new SpringSecurityAuthorProvider();

        // By default, Javers configures MockAuthorProvider, which
        // simply returns "unknown"
        // return new MockAuthorProvider();
    }
}
