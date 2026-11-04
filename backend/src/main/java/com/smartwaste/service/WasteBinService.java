package com.smartwaste.service;

import com.smartwaste.dto.WasteBinDto;

import java.util.List;

/**
 * WasteBinService
 * 
 * Business logic service interface defining operations for managing smart waste bins.
 * Mediates between controllers and the database persistence layer.
 */
public interface WasteBinService {

    /**
     * Retrieve all waste bins.
     */
    List<WasteBinDto> getAllBins();

    /**
     * Retrieve a specific waste bin by its ID.
     */
    WasteBinDto getBinById(Long id);

    /**
     * Retrieve a specific waste bin by its unique bin code.
     */
    WasteBinDto getBinByCode(String binCode);

    /**
     * Create a new waste bin record.
     */
    WasteBinDto createBin(WasteBinDto binDto);

    /**
     * Update fill level of a bin from IoT telemetry.
     */
    WasteBinDto updateFillLevel(Long id, Double fillLevel);

    /**
     * Delete a waste bin by its ID.
     */
    void deleteBin(Long id);
}
