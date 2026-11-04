package com.smartwaste.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 *
 * Configures Spring Security 6 with session-based authentication, role-based
 * authorization, CSRF protection (enabled for browser flows; exempted for the
 * stateless WasteBin REST API), and secure logout.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RoleAwareAuthenticationSuccessHandler successHandler;
    private final RoleAwareAuthenticationFailureHandler failureHandler;
    private final RoleAwareAuthenticationEntryPoint entryPoint;

    public SecurityConfig(RoleAwareAuthenticationSuccessHandler successHandler,
            RoleAwareAuthenticationFailureHandler failureHandler,
            RoleAwareAuthenticationEntryPoint entryPoint) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.entryPoint = entryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF enabled globally; exempt stateless REST API to preserve existing clients
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public static assets, landing page, auth endpoints
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico")
                        .permitAll()
                        .requestMatchers(
                                "/citizen/login",
                                "/citizen/register",
                                "/worker/login",
                                "/admin/login",
                                "/login",
                                "/logout",
                                "/error")
                        .permitAll()
                        // Role-protected areas
                        .requestMatchers("/citizen/**").hasRole("CITIZEN")
                        .requestMatchers("/worker/**").hasRole("WORKER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Preserve existing WasteBin API functionality
                        .requestMatchers("/api/**").permitAll()
                        // Everything else requires authentication
                        .anyRequest().authenticated())

                // Session-based form login
                .formLogin(form -> form
                        .loginPage("/citizen/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .permitAll())

                // Secure logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true))

                // Unauthenticated requests -> role-aware login page
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))

                // Session fixation protection
                .sessionManagement(session -> session
                        .sessionFixation(fixation -> fixation.migrateSession()))

                // Allow frames from same origin (H2 console if needed)
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
