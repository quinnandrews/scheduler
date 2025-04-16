package io.github.quinnandrews.scheduler.modules.administration.schedules.core;

import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnCreate;
import io.github.quinnandrews.scheduler.commons.core.domain.validation.groups.OnUpdate;
import io.github.quinnandrews.scheduler.commons.exceptions.NotFoundException;
import io.github.quinnandrews.scheduler.commons.snapshots.core.domain.SnapshotHistory;
import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location;
import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.Location_;
import io.github.quinnandrews.scheduler.modules.administration.schedules.core.domain.repositories.LocationRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@Validated
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(final LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Validated(OnCreate.class)
    public Location createLocation(@Valid final Location location) {
        return locationRepository.save(location);
    }

    @Validated(OnUpdate.class)
    public Location updateLocation(@Valid final Location location) {
        final var existing = getLocationOrElseThrow(location.getId());
        return locationRepository.save(existing.merge(location));
    }

    public Location getLocationOrElseThrow(final Long id) {
        Objects.requireNonNull(id, "Argument 'id' cannot be null.");
        return locationRepository.findByIdWithCaching(id)
                .orElseThrow(() -> new NotFoundException(Location.class, id));
    }

    public SnapshotHistory<Location, Long> getLocationHistoryOrElseThrow(final Long id) {
        return locationRepository.findSnapshotHistory(Location.class, id)
                .orElseThrow(() -> new NotFoundException(
                        "Could not find History of " +  Location.class.getSimpleName() + " with ID[" + id + "]."));
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAllWithCaching(
                Sort.by(Location_.STATUS)
                        .and(Sort.by(Location_.STATE, Location_.NAME))
        );
    }
}
