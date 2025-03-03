package io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model;

import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public record LocationResponse(
        Integer id,
        Integer version,
        ZonedDateTime utcDateCreated,
        ZonedDateTime utcDateLastModified,
        Location.Status status,
        String name,
        String state,
        String timeZone,
        Double latitude,
        Double longitude,
        Integer radius
) {

    public static LocationResponse of(final Location location) {
        return new LocationResponse(
                location.getId(),
                location.getVersion(),
                Optional.ofNullable(location.getDateCreated())
                        .map(d -> d.atZone(ZoneId.of("UTC")))
                        .orElse(null),
                Optional.ofNullable(location.getDateLastModified())
                        .map(d -> d.atZone(ZoneId.of("UTC")))
                        .orElse(null),
                location.getStatus(),
                location.getName(),
                location.getState(),
                location.getTimeZone(),
                location.getLatitude(),
                location.getLongitude(),
                location.getRadius()
        );
    }
}
