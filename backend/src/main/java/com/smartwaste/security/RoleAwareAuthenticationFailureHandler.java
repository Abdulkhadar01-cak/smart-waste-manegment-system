package com.smartwaste.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RoleAwareAuthenticationFailureHandler
 *
 * Redirects failed login attempts back to the role-specific login page with an
 * appropriate error code. Messages are generic to avoid user enumeration.
 */
@Component
public class RoleAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                       HttpServletResponse response,
                                       AuthenticationException exception) throws IOException {
        String role = request.getParameter("role");
        if (role == null || role.isBlank()) {
            role = "citizen";
        }
        role = role.trim().toLowerCase();

        String errorCode = "invalid";
        if (exception instanceof org.springframework.security.authentication.DisabledException) {
            errorCode = "disabled";
        } else if (exception instanceof org.springframework.security.authentication.LockedException) {
            errorCode = "locked";
        } else if (exception instanceof org.springframework.security.authentication.AccountExpiredException) {
            errorCode = "expired";
        } else if (exception instanceof org.springframework.security.authentication.CredentialsExpiredException) {
            errorCode = "credentials";
        }

        response.sendRedirect("/" + role + "/login?error=" + errorCode);
    }
}
