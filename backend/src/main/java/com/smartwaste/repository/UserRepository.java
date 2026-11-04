package com.smartwaste.repository;

import com.smartwaste.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository
 * 
 * Spring Data JPA repository for User entity operations.
 * Keyed by UUID primary keys.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find a user by their unique email address.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find all users matching a specific role (CITIZEN, WORKER, ADMIN).
     */
    List<User> findByRole(String role);

    /**
     * Find all active users.
     */
    List<User> findByActiveTrue();

    /**
     * Check if an email is already registered.
     */
    boolean existsByEmail(String email);

    // Count registered citizens
long countByRole(String role);

// Count active users by role
long countByRoleAndActiveTrue(String role);
}
