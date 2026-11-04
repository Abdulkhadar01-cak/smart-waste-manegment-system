package com.smartwaste.repository;
import org.springframework.data.jpa.repository.Query;
import com.smartwaste.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * ComplaintRepository
 * 
 * Spring Data JPA repository for Complaint entity operations.
 * Supports status filtering, user lookups, and assignment queries.
 */
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, UUID> {

    /**
     * Find complaints filtered by current status (PENDING, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED).
     */
    List<Complaint> findByStatus(String status);

    /**
     * Find all complaints reported by a specific citizen.
     */
    List<Complaint> findByCreatedById(UUID userId);

    /**
     * Find all complaints assigned to a specific field worker.
     */
    List<Complaint> findByAssignedWorkerId(UUID workerId);

    /**
     * Find complaints by complaint type (OVERFLOW, ILLEGAL_DUMPING, etc.).
     */
    List<Complaint> findByComplaintType(String complaintType);
    // Count all complaints submitted by a specific citizen
long countByCreatedById(UUID userId);

// Count complaints by citizen and status
long countByCreatedByIdAndStatus(UUID userId, String status);

// Get the latest 5 complaints submitted by a citizen
List<Complaint> findTop5ByCreatedByIdOrderByCreatedAtDesc(UUID userId);
// Count complaints by status
long countByStatus(String status);

@Query("SELECT c FROM Complaint c " +
       "LEFT JOIN FETCH c.createdBy " +
       "LEFT JOIN FETCH c.assignedWorker " +
       "ORDER BY c.createdAt DESC")
List<Complaint> findAllWithCreator();

}
