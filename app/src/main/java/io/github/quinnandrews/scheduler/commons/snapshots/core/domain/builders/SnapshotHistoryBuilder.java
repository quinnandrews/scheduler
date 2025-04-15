package io.github.quinnandrews.scheduler.commons.snapshots.core.domain.builders;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.SnapshotHistory;
import org.javers.core.Changes;
import org.javers.core.ChangesByCommit;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.shadow.Shadow;

import java.util.List;
import java.util.Objects;

public class SnapshotHistoryBuilder<T, ID> {

    private Class<T> entityClass;
    private ID entityId;
    private List<CdoSnapshot> cdoSnapshots;
    private List<Shadow<T>> shadows;
    private Changes changes;

    public SnapshotHistoryBuilder() {
        // no-op
    }

    public SnapshotHistoryBuilder<T, ID> withEntityClass(final Class<T> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public SnapshotHistoryBuilder<T, ID> withEntityId(final ID entityId) {
        this.entityId = entityId;
        return this;
    }

    public SnapshotHistoryBuilder<T, ID> withCdoSnapshots(final List<CdoSnapshot> cdoSnapshots) {
        this.cdoSnapshots = cdoSnapshots;
        return this;
    }

    public SnapshotHistoryBuilder<T, ID> withShadows(final List<Shadow<T>> shadows) {
        this.shadows = shadows;
        return this;
    }

    public SnapshotHistoryBuilder<T, ID> withChanges(final Changes changes) {
        this.changes = changes;
        return this;
    }

    public SnapshotHistory<T, ID> build() {
        Objects.requireNonNull(entityClass, "Field 'entityClass' must not be null.");
        Objects.requireNonNull(entityId, "Field 'entityId' must not be null.");
        Objects.requireNonNull(cdoSnapshots, "Field 'cdoSnapshots' must not be null.");
        Objects.requireNonNull(shadows, "Field 'shadows' must not be null.");
        Objects.requireNonNull(changes, "Field 'changes' must not be null.");
        return new SnapshotHistory<T, ID>()
                .withEntityClass(entityClass)
                .withEntityId(entityId)
                .withSnapshots(cdoSnapshots.stream()
                        .map(cdoSnapshot -> {
                            final var shadow = shadows.stream()
                                    .filter(s -> s.getCommitId().equals(cdoSnapshot.getCommitId()))
                                    .findFirst();
                            final var commitChanges = this.changes.groupByCommit().stream()
                                    .filter(c -> c.getCommit().getId().equals(cdoSnapshot.getCommitId()))
                                    .findFirst();
                            return new SnapshotBuilder<T, ID>()
                                    .withEntityClass(entityClass)
                                    .withEntityId(entityId)
                                    .withCdoSnapshot(cdoSnapshot)
                                    .withShadow(shadow.orElse(null))
                                    .withChanges(commitChanges.map(ChangesByCommit::get)
                                            .orElse(null))
                                    .build();
                        }).toList());
    }
}
