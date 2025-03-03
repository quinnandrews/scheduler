package io.github.quinnandrews.scheduler.commons.snapshots.core.domain.repositories;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.VersioningEntity;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.SnapshotHistory;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.builders.SnapshotHistoryBuilder;
import org.javers.core.Changes;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SnapshotRepositoryImpl<T, ID> implements SnapshotRepository<T, ID> {

    private final Javers javers;

    public SnapshotRepositoryImpl(final Javers javers) {
        this.javers = javers;
    }

    @Override
    public Optional<SnapshotHistory<T, ID>> findSnapshotHistory(final Class<T> entityClass,
                                                                final ID entityId) {
        final var cdoSnapshots = findCdoSnapshots(entityClass, entityId);
        final var shadows = findShadows(entityClass, entityId);
        final var changes = findChanges(entityClass, entityId);
        if (!cdoSnapshots.isEmpty()
                && !shadows.isEmpty()
                && !changes.isEmpty()) {
            return Optional.of(
                    new SnapshotHistoryBuilder<T, ID>()
                            .withEntityClass(entityClass)
                            .withEntityId(entityId)
                            .withCdoSnapshots(cdoSnapshots)
                            .withShadows(shadows)
                            .withChanges(changes)
                            .build()
                    );
        }
        return Optional.empty();
    }

    @Override
    public Optional<CdoSnapshot> findSnapshot(final Class<T> entityClass,
                                              final ID entityId,
                                              final Integer entityVersion) {
        return javers.findSnapshots(QueryBuilder.byInstanceId(entityId, entityClass)
                .withCommitProperty(
                        VersioningEntity.VERSION_KEY,
                        String.valueOf(entityVersion))
                .build())
                .stream()
                .findFirst();
    }

    private List<CdoSnapshot> findCdoSnapshots(final Class<T> entityClass,
                                               final ID entityId) {
        return javers.findSnapshots(QueryBuilder.byInstanceId(entityId, entityClass)
                .withScopeCommitDeep()
                .withChildValueObjects()
                .build());
    }

    private List<Shadow<T>> findShadows(final Class<T> entityClass,
                                        final ID entityId) {
        return javers.findShadows(QueryBuilder.byInstanceId(entityId, entityClass)
                .withScopeCommitDeep()
                .build());
    }

    private Changes findChanges(final Class<T> entityClass,
                                final ID entityId) {
        return javers.findChanges(QueryBuilder.byInstanceId(entityId, entityClass)
                .withScopeCommitDeep()
                .withChildValueObjects()
                .build());
    }
}
