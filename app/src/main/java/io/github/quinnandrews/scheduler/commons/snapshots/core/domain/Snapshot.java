package io.github.quinnandrews.scheduler.commons.snapshots.core.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class Snapshot<T, ID> {

    private String commitId;
    private Instant dateCommited;
    private String userCommittedBy;
    private Class<T> entityClass;
    private String entityType;
    private ID entityId;
    private Integer entityVersion;
    private String snapshotChangeType;
    private Long snapshotVersion;
    private T snapshotEntity;
    private Map<String, Object> snapshotProperties;
    private List<String> changedProperties;
    private List<String> changeReport;
    private List<String> changeLog;

    public Snapshot() {
        // no-op
    }

    public String getCommitId() {
        return commitId;
    }

    public Instant getDateCommited() {
        return dateCommited;
    }

    public String getUserCommittedBy() {
        return userCommittedBy;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public String getEntityType() {
        return entityType;
    }

    public ID getEntityId() {
        return entityId;
    }

    public Integer getEntityVersion() {
        return entityVersion;
    }

    public String getSnapshotChangeType() {
        return snapshotChangeType;
    }

    public Long getSnapshotVersion() {
        return snapshotVersion;
    }

    public T getSnapshotEntity() {
        return snapshotEntity;
    }

    public Map<String, Object> getSnapshotProperties() {
        return snapshotProperties;
    }

    public List<String> getChangedProperties() {
        return changedProperties;
    }

    public List<String> getChangeReport() {
        return changeReport;
    }

    public List<String> getChangeLog() {
        return changeLog;
    }

    public Snapshot<T, ID> withCommitId(final String commitId) {
        this.commitId = commitId;
        return this;
    }

    public Snapshot<T, ID> withDateCommited(final Instant dateCommited) {
        this.dateCommited = dateCommited;
        return this;
    }

    public Snapshot<T, ID> withUserCommittedBy(final String userCommittedBy) {
        this.userCommittedBy = userCommittedBy;
        return this;
    }

    public Snapshot<T, ID> withEntityClass(final Class<T> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public Snapshot<T, ID> withEntityType(final String entityType) {
        this.entityType = entityType;
        return this;
    }

    public Snapshot<T, ID> withEntityId(final ID entityId) {
        this.entityId = entityId;
        return this;
    }

    public Snapshot<T, ID> withEntityVersion(final Integer entityVersion) {
        this.entityVersion = entityVersion;
        return this;
    }

    public Snapshot<T, ID> withSnapshotChangeType(final String snapshotChangeType) {
        this.snapshotChangeType = snapshotChangeType;
        return this;
    }

    public Snapshot<T, ID> withSnapshotVersion(final Long snapshotVersion) {
        this.snapshotVersion = snapshotVersion;
        return this;
    }

    public Snapshot<T, ID> withSnapshotEntity(final T snapshotEntity) {
        this.snapshotEntity = snapshotEntity;
        return this;
    }

    public Snapshot<T, ID> withSnapshotProperties(final Map<String, Object> snapshotProperties) {
        this.snapshotProperties = snapshotProperties;
        return this;
    }

    public Snapshot<T, ID> withChangedProperties(final List<String> changedProperties) {
        this.changedProperties = changedProperties;
        return this;
    }

    public Snapshot<T, ID> withChangeReport(final List<String> changeReport) {
        this.changeReport = changeReport;
        return this;
    }

    public Snapshot<T, ID> withChangeLog(final List<String> changeLog) {
        this.changeLog = changeLog;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Snapshot<?, ?> snap)) return false;
        return new EqualsBuilder().append(getCommitId(), snap.getCommitId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getCommitId()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("entityVersion", getEntityVersion())
                .append("commitId", getCommitId())
                .append("dateCommited", getDateCommited())
                .append("userCommittedBy", getUserCommittedBy())
                .append("entityClass", getEntityClass())
                .append("entityType", getEntityType())
                .append("entityId", getEntityId())
                .append("snapshotChangeType", getSnapshotChangeType())
                .append("snapshotVersion", getSnapshotVersion())
                .append("snapshotEntity", getSnapshotEntity())
                .append("snapshotProperties", getSnapshotProperties())
                .append("changedProperties", getChangedProperties())
                .append("changeReport", getChangeReport())
                .append("changeLog", getChangeLog())
                .toString();
    }
}
