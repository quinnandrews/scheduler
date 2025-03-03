package io.github.quinnandrews.scheduler.modules.administration.developer.core.domain;

import java.time.Instant;
import java.util.Map;

public record ApplicationDetails(String environment,
                                 Integer port,
                                 String contextPath,
                                 String name,
                                 String description,
                                 String group,
                                 String artifactId,
                                 String version,
                                 Instant instantBuilt,
                                 Instant instantStarted,
                                 String javaVersion,
                                 String springVersion,
                                 String springBootVersion,
                                 String activeProfiles,
                                 Map<String, String> metrics) {
}
