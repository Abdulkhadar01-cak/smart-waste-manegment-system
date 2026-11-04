package com.smartwaste.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * RoleAwareAuthenticationEntryPoint
 *
 * Chooses the login page based on the requested path so unauthenticated users are
 * sent to the appropriate role login instead of a generic page.
 */
@Component
public class RoleAwareAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String path = request.getRequestURI();
        String loginUrl;
        if (path != null && path.startsWith("/worker/")) {
            loginUrl = "/worker/login";
        } else if (path != null && path.startsWith("/admin/")) {
            loginUrl = "/admin/login";
        } else {
            loginUrl = "/citizen/login";
        }
        response.sendRedirect(loginUrl);
    }
}
