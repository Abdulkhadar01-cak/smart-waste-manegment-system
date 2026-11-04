/**
 * Data Transfer Object (DTO) Layer for the Smart Waste Management System.
 * 
 * Purpose:
 * Contains lightweight request and response payload objects transferred between 
 * the client (frontend/API) and the server.
 * DTOs isolate internal database entity models from external presentation contracts 
 * and enable targeted input validation (@Valid, @NotNull, @Size).
 * 
 * Typical components:
 * - WasteBinRequestDto
 * - WasteBinResponseDto
 * - SensorTelemetryDto
 * - CollectionScheduleDto
 */
package com.smartwaste.dto;
