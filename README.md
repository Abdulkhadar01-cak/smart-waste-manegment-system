
# Smart Waste Management System

A municipal waste monitoring and collection optimization platform built with **Java Spring Boot 3** and **Thymeleaf**.


## Project Folder Architecture

Smart-Waste-Management/
│
├── frontend/
│   ├── html/                         # Wireframes, layout specs & Thymeleaf fragment plans
│   ├── css/                          # Design tokens, color palettes & breakpoint rules
│   ├── js/                           # Client-side architecture & API client patterns
│   └── assets/
│       ├── images/                   # High-res raw source graphics & branding exports
│       └── icons/                    # Raw SVG icon packs
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smartwaste/
│   │   │   │   ├── controller/       # Web & REST controllers (Endpoint handling)
│   │   │   │   ├── service/          # Business logic & transaction management
│   │   │   │   ├── repository/       # Spring Data JPA repositories (Database access)
│   │   │   │   ├── entity/           # JPA entity models (Database table mapping)
│   │   │   │   ├── dto/              # Data Transfer Objects (Request/Response schemas)
│   │   │   │   ├── config/           # Spring configuration classes
│   │   │   │   ├── security/         # Security configuration & role-based access
│   │   │   │   ├── exception/        # Centralized exception handling (@ControllerAdvice)
│   │   │   │   └── SmartWasteApplication.java # Spring Boot main entry point
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── templates/        # Thymeleaf HTML templates (Server-rendered views)
│   │   │       ├── static/           # Static web assets served to clients
│   │   │       │   ├── css/          # External stylesheets (style.css)
│   │   │       │   ├── js/           # External client JavaScript (main.js)
│   │   │       │   └── images/       # Web-optimized image assets & logos
│   │   │       ├── db/migration/     # Versioned Flyway database migration scripts
│   │   │       └── application.properties # Core Spring Boot configuration
│   │   │
│   │   └── test/                     # Unit and MockMvc integration tests
│   └── pom.xml                       # Maven build configuration & dependencies
│
├── database/
│   ├── migrations/                   # DBA migration logs & revision notes
│   ├── schema.sql                    # Full DDL database schema creation script
│   ├── seed.sql                      # Seed data script with demo bins, trucks & users
│   └── README.md                     # Database documentation & setup instructions
│
├── docs/
│   ├── architecture.md               # Detailed architectural design & layer interactions
│   └── setup-guide.md                # Environment setup & verification test instructions
│
├── .gitignore                        # Git ignore rules for Java, Maven, and IDEs
├── .env.example                      # Environment variables template
└── README.md                         # Project overview and folder documentation




## Purpose of Every Folder

### 1. `frontend/`
Contains non-duplicated design guidelines, UI specifications, raw illustrations, and documentation.
- `html/`: Stores UI wireframes, page structure blueprints, and component hierarchy plans.
- `css/`: Documents typography rules, color palettes, and responsive breakpoints.
- `js/`: Documents client scripting conventions, API interaction patterns, and DOM strategies.
- `assets/images/`: Raw branding artwork and master SVG illustrations.
- `assets/icons/`: Raw icon packs and vector glyph sources.

> **Crucial Note**: As this application uses Spring Boot with Thymeleaf, active rendered HTML templates reside in `backend/src/main/resources/templates/`, and active CSS/JS/images reside in `backend/src/main/resources/static/`. This avoids duplication across the repository.

### 2. `backend/`
The complete Java Spring Boot 3 enterprise application.
- `src/main/java/com/smartwaste/`:
  - `controller/`: Intercepts incoming HTTP requests, binds input parameters, and returns Thymeleaf view names or REST responses.
  - `service/`: Implements domain logic, validation rules, collection scheduling calculations, and transactional operations.
  - `repository/`: Spring Data JPA interfaces that interact with the relational database.
  - `entity/`: Java classes annotated with `@Entity` representing database tables (`users`, `waste_bins`, `collection_trucks`, `alerts`).
  - `dto/`: Data Transfer Objects used to validate incoming request bodies and format API responses.
  - `config/`: Spring `@Configuration` beans (MVC config, web resource handlers, OpenAPI).
  - `security/`: Spring Security rules, password hashing, and user authentication.
  - `exception/`: Global `@ControllerAdvice` error handlers and custom domain exceptions.
- `src/main/resources/`:
  - `templates/`: Server-rendered Thymeleaf HTML templates (e.g., `index.html`).
  - `static/css/`: Standalone external CSS stylesheets (`style.css`).
  - `static/js/`: Standalone external JavaScript files (`main.js`).
  - `static/images/`: Logos, icons, and static images (`smart-waste-logo.svg`).
  - `db/migration/`: Versioned migration scripts (`V1__init_schema.sql`) for Flyway.
- `src/test/`: Contains automated integration tests verifying template rendering and static asset delivery.
- `pom.xml`: Maven dependency descriptor declaring Spring Boot starters for Web, Thymeleaf, and Testing.

### 3. `database/`
Houses database DDL, test data seeders, and DBA guidelines.
- `schema.sql`: Full database creation script with tables, foreign keys, and indexes.
- `seed.sql`: Sample records for instant local development (sample IoT bins, collection trucks, drivers).
- `migrations/`: Manual migration logs and release change notes.

### 4. `docs/`
System documentation for developers and stakeholders.
- `architecture.md`: Comprehensive explanation of the layered architecture and data flow.
- `setup-guide.md`: Step-by-step developer guide to compile, test, and run the project.


## Verification: Template & Static Asset Resolution

To verify that Spring Boot correctly locates templates and static resources:

powershell
cd backend
mvn test

All 5 verification tests pass:
1. Context loading
2. Thymeleaf template rendering at `GET /`
3. Static CSS resolution at `GET /css/style.css`
4. Static JS resolution at `GET /js/main.js`
5. Static SVG logo resolution at `GET /images/smart-waste-logo.svg`



