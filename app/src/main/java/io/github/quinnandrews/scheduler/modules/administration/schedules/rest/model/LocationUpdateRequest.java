package io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model;

import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;

public record LocationUpdateRequest(
        Integer version,
        Location.Status status,
        String name,
        String state,
        String timeZone,
        Double latitude,
        Double longitude,
        Integer radius
) {

    public Location toLocation(final Long id) {
        return new Location()
                .withId(id)
                .withStatus(status)
                .withVersion(version)
                .withName(name)
                .withState(state)
                .withTimeZone(timeZone)
                .withLatitude(latitude)
                .withLongitude(longitude)
                .withRadius(radius);
    }
}
