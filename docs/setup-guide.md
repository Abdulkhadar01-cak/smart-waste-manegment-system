# Developer Setup & Verification Guide

This guide provides step-by-step instructions for running the **Smart Waste Management System**, compiling the project, executing verification tests, and confirming that Spring Boot can locate all Thymeleaf templates and static resources.

---

## 1. Prerequisites

- **Java JDK**: Version 17 or higher (Installed: Java 25 LTS).
- **Maven**: Version 3.8+ (Available via portable Apache Maven or Maven Wrapper).
- **Operating System**: Windows / Linux / macOS.
- **Browser**: Chrome, Firefox, Edge, or Safari.

---

## 2. Running Automated Verification Tests

The project includes integration tests that verify:
1. Spring Boot application context loads cleanly.
2. Thymeleaf template engine resolves `classpath:/templates/index.html`.
3. Static CSS is served from `classpath:/static/css/style.css`.
4. Static JS is served from `classpath:/static/js/main.js`.
5. Static images are served from `classpath:/static/images/smart-waste-logo.svg`.

### Run tests using Maven:
```powershell
# Navigate to backend directory
cd backend

# Execute tests
mvn test
```

---

## 3. Starting the Spring Boot Server

Run the development server on `localhost:8080`:

```powershell
cd backend
mvn spring-boot:run
```

Once started:
1. Open your web browser and navigate to:  
   **`http://localhost:8080/`**
2. You will see the **Spring Boot Environment Verification** page.
3. Observe:
   - Green status indicators for Thymeleaf, CSS, JS, and SVG image resolution.
   - External stylesheet applied cleanly without inline CSS.
   - Client-side JavaScript execution timestamp updated by `main.js`.

---

## 4. Database Setup (Optional for Current Phase)

When ready to connect MySQL or PostgreSQL:
1. Configure credentials in:  
   `backend/src/main/resources/application.properties`
2. Run the initialization script:
   ```bash
   mysql -u root -p smart_waste_db < database/schema.sql
   mysql -u root -p smart_waste_db < database/seed.sql
   ```
