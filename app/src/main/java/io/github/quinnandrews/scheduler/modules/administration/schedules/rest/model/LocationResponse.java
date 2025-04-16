package io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model;

import io.github.quinnandrews.scheduler.commons.snapshots.rest.model.AuthorSummaryResponse;
import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;

public record LocationResponse(
        Long id,
        Integer version,
        Location.Status status,
        String name,
        String state,
        String timeZone,
        Double latitude,
        Double longitude,
        Integer radius,
        AuthorSummaryResponse _authorSummary
) {

    public static LocationResponse of(final Location location) {
        return new LocationResponse(
                location.getId(),
                location.getVersion(),
                location.getStatus(),
                location.getName(),
                location.getState(),
                location.getTimeZone(),
                location.getLatitude(),
                location.getLongitude(),
                location.getRadius(),
                AuthorSummaryResponse.of(location.getAuthorSummary())
        );
    }
}
