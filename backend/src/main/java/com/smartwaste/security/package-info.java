/**
 * Security Layer for the Smart Waste Management System.
 * 
 * Purpose:
 * Houses authentication, authorization, role-based access control (RBAC),
 * security filters, password hashing, and token/session management.
 * 
 * Typical components:
 * - SecurityConfig
 * - CustomUserDetailsService
 * - JwtAuthenticationFilter (if token-based APIs are enabled)
 * - RoleHierarchyConfig (Admin, Driver, Citizen)
 */
package com.smartwaste.security;
