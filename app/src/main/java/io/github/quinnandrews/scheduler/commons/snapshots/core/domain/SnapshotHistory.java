package io.github.quinnandrews.scheduler.commons.snapshots.core.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class SnapshotHistory<T, ID> {

    private Class<T> entityClass;
    private ID entityId;
    private List<Snapshot<T, ID>> snapshots;

    public SnapshotHistory() {
        // no-op
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Object getEntityId() {
        return entityId;
    }

    public List<Snapshot<T, ID>> getSnapshots() {
        return snapshots;
    }

    public SnapshotHistory<T, ID> withEntityClass(final Class<T> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public SnapshotHistory<T, ID> withEntityId(final ID entityId) {
        this.entityId = entityId;
        return this;
    }

    public SnapshotHistory<T, ID> withSnapshots(final List<Snapshot<T, ID>> snapshots) {
        this.snapshots = snapshots;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("entityClass", getEntityClass())
                .append("entityId", getEntityId())
                .append("snapshots", getSnapshots())
                .toString();
    }
}
