# System Architecture Documentation

## Smart Waste Management System

---

## 1. Architectural Overview

The **Smart Waste Management System** utilizes a modern, layered monolith architecture powered by **Java Spring Boot 3** and **Thymeleaf**. The system separates business logic, data persistence, client presentation, and database scripts into well-defined domains.

```
+-----------------------------------------------------------------------+
|                             Client Browser                            |
+-----------------------------------------------------------------------+
           |                                              |
           | HTTP Requests (HTML)                         | Static Assets (CSS/JS/SVG)
           v                                              v
+-------------------------------+             +-------------------------+
|     Spring Boot MVC           |             | Spring Boot Static Web  |
|  (DispatcherServlet)          |             |     Resource Handler    |
+-------------------------------+             +-------------------------+
           |                                              |
           v                                              v
+-------------------------------+             +-------------------------+
|   Thymeleaf Template Engine   |             |  resources/static/      |
|  (resources/templates/*.html) |             |  /css, /js, /images     |
+-------------------------------+             +-------------------------+
           |
           v
+-----------------------------------------------------------------------+
|                           Backend Layers                              |
|                                                                       |
|  [Controller] -> [Service] -> [Repository] -> [Entity] (JPA/Hibernate)|
|       |               |                                               |
|     [DTO]       [Exception] & [Security]                              |
+-----------------------------------------------------------------------+
                                   |
                                   v
+-----------------------------------------------------------------------+
|                           Database Layer                              |
|           (MySQL / PostgreSQL / H2 with Flyway Migrations)            |
+-----------------------------------------------------------------------+
```

---

## 2. Directory Structure & Responsibilities

```
Smart-Waste-Management/
│
├── frontend/                     # Non-duplicated frontend specifications & design assets
│   ├── html/                     # UI wireframes & layout design blueprints
│   ├── css/                      # CSS design tokens & typography specs
│   ├── js/                       # Client architecture patterns & documentation
│   └── assets/
│       ├── images/               # High-resolution branding graphics & source mockups
│       └── icons/                # Raw SVG icon packs
│
├── backend/                      # Java Spring Boot application root
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smartwaste/
│   │   │   │   ├── controller/   # Web & REST Controllers (Request routing)
│   │   │   │   ├── service/      # Business logic & transaction boundaries
│   │   │   │   ├── repository/   # Spring Data JPA repositories (Data access)
│   │   │   │   ├── entity/       # Database JPA entities (Table mappings)
│   │   │   │   ├── dto/          # Data Transfer Objects (Payload models)
│   │   │   │   ├── config/       # Spring @Configuration classes
│   │   │   │   ├── security/     # Authentication, RBAC & security filters
│   │   │   │   ├── exception/    # Global exception handlers (@ControllerAdvice)
│   │   │   │   └── SmartWasteApplication.java # Spring Boot main entry point
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── templates/    # Thymeleaf HTML templates (index.html, etc.)
│   │   │       ├── static/       # Static assets served directly to the browser
│   │   │       │   ├── css/      # External stylesheets (style.css)
│   │   │       │   ├── js/       # External scripts (main.js)
│   │   │       │   └── images/   # Web-optimized logos and graphics
│   │   │       ├── db/migration/ # Flyway database versioned migration scripts
│   │   │       └── application.properties # Spring configuration
│   │   │
│   │   └── test/                 # JUnit 5 & MockMvc unit and integration tests
│   └── pom.xml                   # Maven dependencies and build configuration
│
├── database/                     # Standalone database management scripts
│   ├── migrations/               # Manual / DBA migration logs
│   ├── schema.sql                # Complete DDL table definition script
│   ├── seed.sql                  # Initial development demo data
│   └── README.md                 # Database setup and querying guide
│
├── docs/                         # System documentation
│   ├── architecture.md           # Architecture breakdown (this document)
│   └── setup-guide.md            # Developer setup and execution instructions
│
├── .gitignore                    # Git ignore rules for Java, IDEs, and build artifacts
├── .env.example                  # Environment variable template
└── README.md                     # Project overview and quickstart
```

---

## 3. Strict Separation of Concerns Rules

To ensure maintainability and high code quality, this project adheres to the following strict conventions:

1. **Separation of Presentation Technologies**:
   - **HTML**: Exclusively inside `backend/src/main/resources/templates/`. Contains only semantic markup and Thymeleaf attributes (`th:*`).
   - **CSS**: Exclusively inside `backend/src/main/resources/static/css/`. **No large inline `<style>` tags in HTML templates.**
   - **JavaScript**: Exclusively inside `backend/src/main/resources/static/js/`. **No large inline `<script>` tags in HTML templates.**
   - **Static Images**: Exclusively inside `backend/src/main/resources/static/images/`.

2. **Backend Decoupling**:
   - Controllers handle HTTP transport and delegate immediately to Services.
   - Services execute business rules and never contain SQL or direct web request logic.
   - Repositories handle data persistence using Spring Data JPA.
   - Entities represent database tables and are not exposed directly in public APIs (DTOs are used instead).

3. **Database Script Organization**:
   - Schema definitions (`schema.sql`) and initial data (`seed.sql`) are isolated in `database/`.
   - Production migrations reside in `backend/src/main/resources/db/migration/` to support automated Flyway execution.
