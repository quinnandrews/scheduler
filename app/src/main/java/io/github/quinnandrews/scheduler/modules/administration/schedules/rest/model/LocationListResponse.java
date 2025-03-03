package io.github.quinnandrews.scheduler.modules.administration.schedules.rest.model;

import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;

import java.util.List;

public record LocationListResponse(List<LocationResponse> locations) {

    public static LocationListResponse of(final List<Location> locations) {
        return new LocationListResponse(
                locations.stream()
                        .map(LocationResponse::of)
                        .toList()
        );
    }
}
