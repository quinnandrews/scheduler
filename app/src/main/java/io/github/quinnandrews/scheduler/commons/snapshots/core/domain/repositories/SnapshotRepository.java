package io.github.quinnandrews.scheduler.commons.snapshots.core.domain.repositories;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.SnapshotHistory;
import org.javers.core.metamodel.object.CdoSnapshot;

import java.util.Optional;

public interface SnapshotRepository<T, ID> {

    Optional<SnapshotHistory<T, ID>> findSnapshotHistory(Class<T> entityClass, ID entityId);

    Optional<CdoSnapshot> findSnapshot(Class<T> entityClass, ID entityId, Integer entityVersion);
}
