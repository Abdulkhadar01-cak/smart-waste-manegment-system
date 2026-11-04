package com.smartwaste.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * WasteBin Entity
 * 
 * Maps to database table 'waste_bins'.
 * Represents a physical smart waste receptacle equipped with IoT fill-level sensors.
 */
@Entity
@Table(name = "waste_bins")
public class WasteBin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bin_code", nullable = false, unique = true, length = 30)
    private String binCode;

    @Column(name = "waste_type", nullable = false, length = 30)
    private String wasteType = "GENERAL"; // GENERAL, ORGANIC, RECYCLABLE, HAZARDOUS

    @Column(name = "capacity_liters", nullable = false)
    private Double capacityLiters;

    @Column(name = "fill_level_percentage")
    private Double fillLevelPercentage = 0.0;

    @Column(name = "battery_percentage")
    private Double batteryPercentage = 100.0;

    @Column(nullable = false, length = 30)
    private String status = "ACTIVE"; // ACTIVE, MAINTENANCE, FULL, OFFLINE

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 255)
    private String address;

    @Column(name = "zone_area", length = 100)
    private String zoneArea;

    @Column(name = "last_collection_at")
    private LocalDateTime lastCollectionAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public WasteBin() {
    }

    public WasteBin(String binCode, String wasteType, Double capacityLiters, Double latitude, Double longitude, String address) {
        this.binCode = binCode;
        this.wasteType = wasteType;
        this.capacityLiters = capacityLiters;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
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

    public LocalDateTime getLastCollectionAt() {
        return lastCollectionAt;
    }

    public void setLastCollectionAt(LocalDateTime lastCollectionAt) {
        this.lastCollectionAt = lastCollectionAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
