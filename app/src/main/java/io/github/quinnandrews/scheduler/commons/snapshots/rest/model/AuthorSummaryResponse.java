package io.github.quinnandrews.scheduler.commons.snapshots.rest.model;

import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.AuthorSummary;

import java.time.Instant;

public record AuthorSummaryResponse(
        Instant dateCreated,
        Instant dateLastModified
) {

    public static AuthorSummaryResponse of(final AuthorSummary summary) {
        return new AuthorSummaryResponse(
                summary.getDateCreated(),
                summary.getDateLastModified()
        );
    }
}
