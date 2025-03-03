package io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model;

import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;

public record LocationCreateRequest(
        Location.Status status,
        String name,
        String state,
        String timeZone,
        Double latitude,
        Double longitude,
        Integer radius
) {

    public Location toLocation() {
        return new Location()
                .withStatus(status)
                .withName(name)
                .withState(state)
                .withTimeZone(timeZone)
                .withLatitude(latitude)
                .withLongitude(longitude)
                .withRadius(radius);
    }
}
