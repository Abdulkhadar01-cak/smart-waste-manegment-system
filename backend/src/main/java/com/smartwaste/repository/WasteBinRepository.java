package com.smartwaste.repository;

import com.smartwaste.entity.WasteBin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * WasteBinRepository
 * 
 * Spring Data JPA repository for WasteBin entities.
 * Automatically generates SQL implementations for CRUD operations,
 * pagination, and custom query methods.
 */
@Repository
public interface WasteBinRepository extends JpaRepository<WasteBin, Long> {

    /**
     * Find a waste bin by its unique code (e.g. "BIN-DT-001").
     */
    Optional<WasteBin> findByBinCode(String binCode);

    /**
     * Find bins filtered by their operating status (ACTIVE, FULL, MAINTENANCE, OFFLINE).
     */
    List<WasteBin> findByStatus(String status);

    /**
     * Find bins whose fill level percentage exceeds or equals a specified threshold (e.g. >= 80%).
     */
    List<WasteBin> findByFillLevelPercentageGreaterThanEqual(Double fillLevelThreshold);

    /**
     * Check if a bin code already exists.
     */
    boolean existsByBinCode(String binCode);
}
