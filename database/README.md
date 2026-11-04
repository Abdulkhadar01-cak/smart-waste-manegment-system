# Database Layer Documentation

This directory houses all SQL scripts, relational schemas, initial data seeders, and migration guidelines for the **Smart Waste Management System**.

---

## Directory Contents

| File / Folder | Purpose |
| :--- | :--- |
| `schema.sql` | Complete DDL (Data Definition Language) script creating tables, foreign key constraints, indexes, and column validations. |
| `seed.sql` | Initial demo dataset (users, smart bins, trucks, alerts) for local testing and UI verification. |
| `migrations/` | Versioned SQL migration scripts (e.g., `V1__init.sql`, `V2__add_index.sql`) tracking incremental schema evolution. |

---

## Architecture Note: Spring Boot Migration Integration

While raw standalone SQL scripts are maintained in this `database/` directory for DBA tasks and reference, in Spring Boot applications using automated migration tools (such as **Flyway** or **Liquibase**):

- Migration scripts are located at:  
  `backend/src/main/resources/db/migration/`
- This ensures Spring Boot automatically runs migrations on startup when configured.

---

## Quick Execution

### For MySQL:
```bash
mysql -u root -p smart_waste_db < database/schema.sql
mysql -u root -p smart_waste_db < database/seed.sql
```

### For PostgreSQL:
```bash
psql -U postgres -d smart_waste_db -f database/schema.sql
psql -U postgres -d smart_waste_db -f database/seed.sql
```
