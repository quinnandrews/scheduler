package io.github.quinnandrews.scheduler.commons.snapshots.core.domain.builders;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.Snapshot;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.VersioningEntity;
import org.javers.common.string.PrettyValuePrinter;
import org.javers.core.diff.Change;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.shadow.Shadow;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SnapshotBuilder<T, ID> {

    private Class<T> entityClass;
    private ID entityId;
    private CdoSnapshot cdoSnapshot;
    private Shadow<T> shadow;
    private List<Change> changes;

    public SnapshotBuilder() {
        // no-op
    }

    public SnapshotBuilder<T, ID> withEntityClass(final Class<T> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public SnapshotBuilder<T, ID> withEntityId(final ID entityId) {
        this.entityId = entityId;
        return this;
    }

    public SnapshotBuilder<T, ID> withCdoSnapshot(final CdoSnapshot cdoSnapshot) {
        this.cdoSnapshot = cdoSnapshot;
        return this;
    }

    public SnapshotBuilder<T, ID> withShadow(final Shadow<T> shadow) {
        this.shadow = shadow;
        return this;
    }

    public SnapshotBuilder<T, ID> withChanges(final List<Change> changes) {
        this.changes = changes;
        return this;
    }

    public Snapshot<T, ID> build() {
        Objects.requireNonNull(entityClass, "Field 'entityClass' must not be null.");
        Objects.requireNonNull(entityId, "Field 'entityId' must not be null.");
        Objects.requireNonNull(cdoSnapshot, "Field 'cdoSnapshot' must not be null.");
        Objects.requireNonNull(shadow, "Field 'shadow' must not be null.");
        Objects.requireNonNull(changes, "Field 'changes' must not be null.");
        final var commitMetaData = cdoSnapshot.getCommitMetadata();
        return new Snapshot<T, ID>()
                .withCommitId(commitMetaData.getId().value())
                .withDateCommited(commitMetaData.getCommitDateInstant())
                .withUserCommittedBy(commitMetaData.getAuthor())
                .withEntityClass(entityClass)
                .withEntityType(cdoSnapshot.getManagedType().getName())
                .withEntityId(entityId)
                .withEntityVersion(
                        Optional.ofNullable(commitMetaData.getProperties().get(VersioningEntity.VERSION_KEY))
                                .map(Integer::valueOf)
                                .orElse(null))
                .withSnapshotChangeType(cdoSnapshot.getType().name())
                .withSnapshotVersion(cdoSnapshot.getVersion())
                .withSnapshotEntity(shadow.get())
                .withSnapshotProperties(
                        cdoSnapshot.getState()
                                .mapProperties(SnapshotProperty::new).stream()
                                .collect(Collectors.toUnmodifiableMap(SnapshotProperty::name, SnapshotProperty::value))
                )
                .withChangedProperties(cdoSnapshot.getChanged())
                .withChangeReport(
                        changes.stream()
                                .map(c -> c.prettyPrint(PrettyValuePrinter.getDefault()))
                                .toList())
                .withChangeLog(
                        changes.stream()
                                .map(Change::toString)
                                .toList()
                );
    }

    private record SnapshotProperty(String name, Object value)  {
    }
}
