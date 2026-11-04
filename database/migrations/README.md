# Database Migrations

This folder tracks incremental schema changes for manual deployment or tracking.

When using automated Spring Boot migration tools (such as Flyway):
- Production-bound versioned migrations must reside in:
  `backend/src/main/resources/db/migration/`
- Naming convention: `V<Version>__<Description>.sql` (e.g., `V1__init_schema.sql`, `V2__add_sensor_metrics.sql`).
