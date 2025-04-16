package io.github.quinnandrews.scheduler.commons.snapshots.core.domain;

public interface VersionedEntity {

    String VERSION_KEY = "entity.version";

    Integer getVersion();
}
