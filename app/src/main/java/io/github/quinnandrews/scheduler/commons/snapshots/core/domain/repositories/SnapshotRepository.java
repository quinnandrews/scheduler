package io.github.quinnandrews.scheduler.commons.snapshots.core.domain.repositories;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.Snapshot;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.SnapshotHistory;

import java.util.Optional;

public interface SnapshotRepository<T, I> {

    Optional<SnapshotHistory<T, I>> findSnapshotHistory(Class<T> entityClass, I entityId);

    Optional<Snapshot<T, I>> findSnapshot(Class<T> entityClass, I entityId, Integer entityVersion);
}
