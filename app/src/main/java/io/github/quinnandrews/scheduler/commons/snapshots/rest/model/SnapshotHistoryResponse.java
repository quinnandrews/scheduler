package io.github.quinnandrews.scheduler.commons.snapshots.rest.model;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.SnapshotHistory;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

public record SnapshotHistoryResponse<T, ID>(

        @Schema(description = """
                The name of the Entity Class/Aggregate Root whose version history for a particular instance \
                had been requested.""")
        String entityClass,

        @Schema(description = """
                The ID of the Entity Class/Aggregate Root instance whose version history had been requested.""")
        Object entityId,

        @ArraySchema(schema = @Schema(implementation = SnapshotResponse.class),
                arraySchema = @Schema(description = """
                The version history belonging to the given Entity Class/Aggregate Root instance."""))
        List<SnapshotResponse<T, ID>> snapshots
) {

    public static <T, ID> SnapshotHistoryResponse<T, ID> of(final SnapshotHistory<T, ID> history) {
        Objects.requireNonNull(history, "Argument 'history' must not be null.");
        return new SnapshotHistoryResponse<>(
                history.getEntityClass().getName(),
                history.getEntityId(),
                history.getSnapshots().stream()
                        .map(SnapshotResponse::of)
                        .toList()
        );
    }
}
