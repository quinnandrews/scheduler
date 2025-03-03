package io.github.quinnandrews.scheduler.commons.snapshots.core.domain;

public interface VersioningEntity {

    String VERSION_KEY = "entity.version";

    Integer getVersion();
}
