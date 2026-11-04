package com.smartwaste.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RoleAwareAuthenticationSuccessHandler
 *
 * Redirects authenticated users to their role-specific dashboard:
 *   CITIZEN -> /citizen/dashboard
 *   WORKER  -> /worker/dashboard
 *   ADMIN   -> /admin/dashboard
 *
 * Also enforces that the requested login role (from the form) matches the
 * authenticated account's role, preventing cross-role login.
 */
@Component
public class RoleAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String requestedRole = request.getParameter("role");

        if (requestedRole != null && !requestedRole.isBlank()) {
            String requestedAuthority = "ROLE_" + requestedRole.trim().toUpperCase();
            boolean hasRequestedRole = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(requestedAuthority::equals);
            if (!hasRequestedRole) {
                // Invalidate the just-created session and reject
                request.getSession().invalidate();
                org.springframework.security.core.context.SecurityContextHolder.clearContext();
                response.sendRedirect("/" + requestedRole.trim().toLowerCase() + "/login?error=role");
                return;
            }
        }

        String targetUrl = "/";
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String a = authority.getAuthority();
            if ("ROLE_CITIZEN".equals(a)) {
                targetUrl = "/citizen/dashboard";
                break;
            } else if ("ROLE_WORKER".equals(a)) {
                targetUrl = "/worker/dashboard";
                break;
            } else if ("ROLE_ADMIN".equals(a)) {
                targetUrl = "/admin/dashboard";
                break;
            }
        }
        response.sendRedirect(targetUrl);
    }
}
