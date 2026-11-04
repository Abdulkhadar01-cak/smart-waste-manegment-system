package com.smartwaste.config;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FlywayConfig
 * 
 * Customizes the Flyway migration lifecycle.
 * Executes flyway.repair() prior to flyway.migrate() to automatically heal
 * any previously failed or aborted migration states in flyway_schema_history
 * before executing pending schema migrations.
 */
@Configuration
public class FlywayConfig {

    private static final Logger log = LoggerFactory.getLogger(FlywayConfig.class);

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return (Flyway flyway) -> {
            try {
                log.info("[Flyway] Performing pre-migration repair on schema history table...");
                flyway.repair();
                log.info("[Flyway] Schema history table repair completed successfully.");
            } catch (Exception e) {
                log.warn("[Flyway] Repair skipped or not needed: {}", e.getMessage());
            }

            log.info("[Flyway] Executing database migrations against Supabase PostgreSQL...");
            int appliedMigrations = flyway.migrate().migrationsExecuted;
            log.info("[Flyway] Database migration finished successfully. Applied migrations: {}", appliedMigrations);
        };
    }
}
