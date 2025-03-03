package io.github.quinnandrews.scheduler.config.tasks;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "2m")
@Configuration
public class ScheduledTaskConfig {

    private final String shedLockTableName;

    public ScheduledTaskConfig(@Value("${example.shedlock.table.name}")
                               final String shedLockTableName) {
        this.shedLockTableName = shedLockTableName;
    }

    @Bean
    public LockProvider lockProvider(final DataSource dataSource) {
        return new JdbcTemplateLockProvider(JdbcTemplateLockProvider.Configuration.builder()
                .withJdbcTemplate(new JdbcTemplate(dataSource))
                .withTableName(shedLockTableName)
                .usingDbTime()
                .build());
    }
}
