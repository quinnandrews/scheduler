package io.github.quinnandrews.scheduler.commons.snapshots.rest.model;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.Snapshot;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record SnapshotResponse<T, ID>(

        @Schema(description = """
                An I representing the operation that made the changes. Similar to the \
                concept of a transaction, but not identical.""")
        String commitId,

        @Schema(description = """
                The time when the changes took place. In UTC time.""")
        Instant dateCommitted,

        @Schema(nullable = true,
                description = """
                A human-readable identifier for the person who made the changes, if known.""")
        String authorCommitedBy,

        @Schema(description = """
                The fully qualified name of the Entity Class/Aggregate Root.""")
        Class<T> entityClass,

        @Schema(nullable = true,
                description = """
                The type name of the Entity Class/Aggregate Root.""")
        String entityType,

        @Schema(nullable = true,
                description = """
                The I of the Entity Class/Aggregate Root instance that was changed.""")
        ID entityId,

        @Schema(nullable = true,
                description = """
                The version of the Entity Class/Aggregate Root's instance after the changes were made. In other words, \
                the value of the Entity's version property after changes were persisted and its value incremented, if \
                the Entity has a version property and it was set as a Commit Property.""")
        Integer entityVersion,

        @Schema(description = """
                The kind of operation that took place, whether the operation created, updated or deleted the instance of \
                the Entity Class/Aggregate Root.""")
        String snapshotChangeType,

        @Schema(description = """
                The version of the Entity Class/Aggregate Root instance as recorded by the version history framework. \
                Does not necessarily match the Entity or Aggregate's version as recorded by the Entity or Aggregate itself.""")
        Long snapshotVersion,

        @Schema(description = """
                The state of the Entity Class/Aggregate Root instance once the changes were made.""")
        T snapshotEntity,

        @ArraySchema(arraySchema = @Schema(description = """
                The properties of the Entity Class/Aggregate Root and their state at the time of the change. May \
                include properties that are no longer a member of the Entity/Aggregate Root."""))
        Map<String, Object> snapshotProperties,

        @ArraySchema(arraySchema = @Schema(description = """
                The properties of the Entity Class/Aggregate Root that changed."""))
        List<String> changedProperties,

        @ArraySchema(arraySchema = @Schema(description = """
                A List of messages describing each property of the Entity Class/Aggregate Root that changed and \
                how they changed, from their old values to their new values. Suitable for any audience, developers \
                and users alike."""))
        List<String> changeReport,

        @ArraySchema(arraySchema = @Schema(description = """
                A List of messages describing each property of the Entity Class/Aggregate Root that changed and \
                how they changed, from their old values to their new values. These messages are more technical in \
                nature and are only meant for use by developers."""))
        List<String> changeLog
) {

    public static <T, I> SnapshotResponse<T, I> of(final Snapshot<T, I> snapshot) {
        Objects.requireNonNull(snapshot, "Argument 'snapshot' must not be null.");
        return new SnapshotResponse<>(
                snapshot.getCommitId(),
                snapshot.getDateCommited(),
                snapshot.getUserCommittedBy(),
                snapshot.getEntityClass(),
                snapshot.getEntityType(),
                snapshot.getEntityId(),
                snapshot.getEntityVersion(),
                snapshot.getSnapshotChangeType(),
                snapshot.getSnapshotVersion(),
                snapshot.getSnapshotEntity(),
                snapshot.getSnapshotProperties(),
                snapshot.getChangedProperties(),
                snapshot.getChangeReport(),
                snapshot.getChangeLog()
        );
    }
}
