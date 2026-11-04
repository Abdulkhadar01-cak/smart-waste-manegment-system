package com.smartwaste.dto;

import jakarta.validation.constraints.*;

/**
 * WasteBinDto
 * 
 * Data Transfer Object for creating and updating WasteBin records.
 * Decouples presentation payloads from internal JPA database entities
 * and enforces strict field validation constraints.
 */
public class WasteBinDto {

    private Long id;

    @NotBlank(message = "Bin code cannot be blank")
    @Size(min = 3, max = 30, message = "Bin code must be between 3 and 30 characters")
    private String binCode;

    @NotBlank(message = "Waste type cannot be blank")
    private String wasteType;

    @NotNull(message = "Capacity in liters is required")
    @Positive(message = "Capacity must be greater than zero")
    private Double capacityLiters;

    @Min(value = 0, message = "Fill level percentage cannot be less than 0")
    @Max(value = 100, message = "Fill level percentage cannot exceed 100")
    private Double fillLevelPercentage = 0.0;

    @Min(value = 0, message = "Battery percentage cannot be less than 0")
    @Max(value = 100, message = "Battery percentage cannot exceed 100")
    private Double batteryPercentage = 100.0;

    private String status = "ACTIVE";

    @NotNull(message = "Latitude coordinate is required")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private Double latitude;

    @NotNull(message = "Longitude coordinate is required")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private Double longitude;

    private String address;
    private String zoneArea;

    public WasteBinDto() {
    }

    public WasteBinDto(Long id, String binCode, String wasteType, Double capacityLiters,
                       Double fillLevelPercentage, String status, Double latitude, Double longitude, String address) {
        this.id = id;
        this.binCode = binCode;
        this.wasteType = wasteType;
        this.capacityLiters = capacityLiters;
        this.fillLevelPercentage = fillLevelPercentage;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public Double getCapacityLiters() {
        return capacityLiters;
    }

    public void setCapacityLiters(Double capacityLiters) {
        this.capacityLiters = capacityLiters;
    }

    public Double getFillLevelPercentage() {
        return fillLevelPercentage;
    }

    public void setFillLevelPercentage(Double fillLevelPercentage) {
        this.fillLevelPercentage = fillLevelPercentage;
    }

    public Double getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Double batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZoneArea() {
        return zoneArea;
    }

    public void setZoneArea(String zoneArea) {
        this.zoneArea = zoneArea;
    }
}
