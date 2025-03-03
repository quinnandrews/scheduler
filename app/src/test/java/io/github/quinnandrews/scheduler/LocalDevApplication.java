package io.github.quinnandrews.scheduler;

import io.github.quinnandrews.scheduler.timezones.TimeZoneUtil;
import io.github.quinnandrews.spring.local.postgresql.config.EnableLocalPostgreSQL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EnableLocalPostgreSQL
@Profile(LocalDevApplication.LOCAL_PROFILE)
@SpringBootApplication
public class LocalDevApplication {

	public static final String LOCAL_PROFILE = "local";

	public static void main(final String[] args) {
		final var springApplication = new SpringApplication(Application.class);
		springApplication.setAdditionalProfiles(LOCAL_PROFILE);
		springApplication.run(args);
	}

	@Profile(LOCAL_PROFILE)
	@Configuration
	public static class LocalDevConfig {

		public LocalDevConfig() {
			TimeZoneUtil.setUTCAsDefault();
		}
	}
}
