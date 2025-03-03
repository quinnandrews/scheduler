package io.github.quinnandrews.scheduler.modules.schedules.rest.model;

import io.github.quinnandrews.scheduler.modules.schedules.core.domain.LocationOption;

public record LocationOptionResponse(
        Integer id,
        String name,
        String state,
        String timeZone,
        Double latitude,
        Double longitude,
        Integer radius
) {

    public static LocationOptionResponse of(final LocationOption location) {
        return new LocationOptionResponse(
                location.getId(),
                location.getName(),
                location.getState(),
                location.getTimeZone(),
                location.getLatitude(),
                location.getLongitude(),
                location.getRadius()
        );
    }
}
