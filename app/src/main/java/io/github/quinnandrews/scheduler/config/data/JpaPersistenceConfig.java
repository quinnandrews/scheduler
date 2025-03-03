package io.github.quinnandrews.scheduler.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "io.github.quinnandrews.scheduler")
@EnableJpaAuditing
@Configuration
public class JpaPersistenceConfig {

    @Bean
    public EmployeeAuditorProvider employeeAuditorProvider() {
        return new EmployeeAuditorProvider();
    }

    public static class EmployeeAuditorProvider implements AuditorAware<String> {

        @NonNull
        @Override
        public Optional<String> getCurrentAuditor() {
            return Optional.of("qandrews@example.com");
        }
    }
}