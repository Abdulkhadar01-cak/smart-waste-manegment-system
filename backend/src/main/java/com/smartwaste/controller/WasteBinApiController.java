package com.smartwaste.controller;

import com.smartwaste.dto.WasteBinDto;
import com.smartwaste.service.WasteBinService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * WasteBinApiController
 * 
 * REST Controller exposing standard API endpoints for smart waste bin operations.
 * Demonstrates request validation (@Valid) and delegation to the service layer.
 */
@RestController
@RequestMapping("/api/v1/bins")
public class WasteBinApiController {

    private final WasteBinService wasteBinService;

    public WasteBinApiController(WasteBinService wasteBinService) {
        this.wasteBinService = wasteBinService;
    }

    /**
     * GET /api/v1/bins - Retrieve all waste bins.
     */
    @GetMapping
    public ResponseEntity<List<WasteBinDto>> getAllBins() {
        return ResponseEntity.ok(wasteBinService.getAllBins());
    }

    /**
     * GET /api/v1/bins/{id} - Retrieve a specific waste bin by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WasteBinDto> getBinById(@PathVariable Long id) {
        return ResponseEntity.ok(wasteBinService.getBinById(id));
    }

    /**
     * POST /api/v1/bins - Create a new smart waste bin with validation.
     */
    @PostMapping
    public ResponseEntity<WasteBinDto> createBin(@Valid @RequestBody WasteBinDto binDto) {
        WasteBinDto created = wasteBinService.createBin(binDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * PATCH /api/v1/bins/{id}/fill-level - Update bin fill level from IoT sensor telemetry.
     */
    @PatchMapping("/{id}/fill-level")
    public ResponseEntity<WasteBinDto> updateFillLevel(
            @PathVariable Long id,
            @RequestParam Double level) {
        return ResponseEntity.ok(wasteBinService.updateFillLevel(id, level));
    }

    /**
     * DELETE /api/v1/bins/{id} - Delete a bin by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBin(@PathVariable Long id) {
        wasteBinService.deleteBin(id);
        return ResponseEntity.noContent().build();
    }
}
