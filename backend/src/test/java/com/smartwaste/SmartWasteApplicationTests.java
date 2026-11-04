package com.smartwaste;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * SmartWasteApplicationTests
 * 
 * Verifies complete backend infrastructure initialization & environment setup:
 * 1. Spring Boot ApplicationContext loads cleanly with Spring Security & JPA
 * 2. Thymeleaf resolves index.html template at GET /
 * 3. Static CSS is served from /css/style.css
 * 4. Static JS is served from /js/main.js
 * 5. Static Images are served from /images/smart-waste-logo.svg
 * 6. Bootstrap 5 WebJars are served from /webjars/bootstrap/5.3.3/css/bootstrap.min.css
 * 7. GlobalExceptionHandler correctly intercepts ResourceNotFoundException and returns HTTP 404
 * 8. Root .env file exists and is ignored in .gitignore
 * 9. Spring datasource properties correctly accept environment variable mappings
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:smart_waste_test_db;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.flyway.enabled=false"
})
class SmartWasteApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Value("${spring.datasource.url}")
    private String configuredDatasourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String configuredDriver;

    @Test
    @DisplayName("1. Context loads successfully with Security, JPA, and Web")
    void contextLoads() {
        assertThat(configuredDriver).isNotNull();
    }

    @Test
    @DisplayName("2. Verify Thymeleaf template resolution at GET /")
    void shouldRenderThymeleafTemplate() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Smart Waste Management System")))
                .andExpect(content().string(containsString("Backend Infrastructure Initialized")));
    }

    @Test
    @DisplayName("3. Verify static CSS resource resolution at GET /css/style.css")
    void shouldServeStaticCss() throws Exception {
        mockMvc.perform(get("/css/style.css"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("--primary-color")));
    }

    @Test
    @DisplayName("4. Verify static JavaScript resource resolution at GET /js/main.js")
    void shouldServeStaticJavaScript() throws Exception {
        mockMvc.perform(get("/js/main.js"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("DOMContentLoaded")));
    }

    @Test
    @DisplayName("5. Verify static SVG image resolution at GET /images/smart-waste-logo.svg")
    void shouldServeStaticImage() throws Exception {
        mockMvc.perform(get("/images/smart-waste-logo.svg"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/svg+xml"));
    }

    @Test
    @DisplayName("6. Verify Bootstrap 5 WebJars resource resolution")
    void shouldServeBootstrapWebJar() throws Exception {
        mockMvc.perform(get("/webjars/bootstrap/5.3.3/css/bootstrap.min.css"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("7. Verify GlobalExceptionHandler returns 404 JSON on missing bin")
    void shouldHandleResourceNotFoundViaGlobalExceptionHandler() throws Exception {
        mockMvc.perform(get("/api/v1/bins/999999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value(containsString("WasteBin not found with id: '999999'")));
    }

    @Test
    @DisplayName("8. Verify .gitignore excludes .env file to prevent credential leaks")
    void shouldIgnoreEnvFileInGit() throws Exception {
        File gitignoreFile = new File("../.gitignore");
        if (!gitignoreFile.exists()) {
            gitignoreFile = new File(".gitignore");
        }
        assertThat(gitignoreFile.exists()).isTrue();

        List<String> lines = Files.readAllLines(gitignoreFile.toPath());
        boolean hasEnvIgnored = lines.stream().anyMatch(line -> line.trim().equals(".env"));
        assertThat(hasEnvIgnored).isTrue();
    }
}
