package com.smartwaste.repository;

import com.smartwaste.entity.WorkAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * WorkAssignmentRepository
 * 
 * Spring Data JPA repository for WorkAssignment entity operations.
 * Facilitates worker assignment lookups and complaint dispatch tracking.
 */
@Repository
public interface WorkAssignmentRepository extends JpaRepository<WorkAssignment, UUID> {

    /**
     * Find all work assignments assigned to a specific worker.
     */
    List<WorkAssignment> findByWorkerId(UUID workerId);

    /**
     * Find work assignment record for a specific complaint.
     */
    Optional<WorkAssignment> findByComplaintId(UUID complaintId);

    /**
     * Find incomplete assignments for a worker (where completedAt is null).
     */
    List<WorkAssignment> findByWorkerIdAndCompletedAtIsNull(UUID workerId);
}
