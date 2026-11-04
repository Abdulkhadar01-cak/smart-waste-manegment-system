-- =============================================================================
-- Migration: V1__init_schema.sql
-- Smart Waste Management System - Core Database Schema
-- Compatible with PostgreSQL 13+ (Supabase) and Flyway
-- =============================================================================

-- Ensure cryptographic extension for UUID generation is available
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- -----------------------------------------------------------------------------
-- 1. USER Table
-- Stores citizens, field workers, and municipal administrators
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'CITIZEN', -- CITIZEN, WORKER, ADMIN
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------------------------------
-- 2. COMPLAINT Table
-- Stores waste reports logged by citizens (overflow, illegal dumping, etc.)
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS complaints (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    complaint_type VARCHAR(50) NOT NULL, -- OVERFLOW, ILLEGAL_DUMPING, DAMAGED_BIN, MISSED_COLLECTION
    description TEXT,
    image_url VARCHAR(500),
    address VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING', -- PENDING, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED
    created_by UUID,
    assigned_worker UUID,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_complaint_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_complaint_worker FOREIGN KEY (assigned_worker) REFERENCES users(id) ON DELETE SET NULL
);

-- -----------------------------------------------------------------------------
-- 3. WORK_ASSIGNMENT Table
-- Tracks task dispatch and resolution workflow by municipal workers
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS work_assignments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    complaint_id UUID NOT NULL,
    worker_id UUID NOT NULL,
    assigned_by UUID,
    assigned_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMPTZ,
    CONSTRAINT fk_assignment_complaint FOREIGN KEY (complaint_id) REFERENCES complaints(id) ON DELETE CASCADE,
    CONSTRAINT fk_assignment_worker FOREIGN KEY (worker_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_assignment_assigner FOREIGN KEY (assigned_by) REFERENCES users(id) ON DELETE SET NULL
);

-- -----------------------------------------------------------------------------
-- Performance Indexes & Foreign Key Optimization
-- -----------------------------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users (role);
CREATE INDEX IF NOT EXISTS idx_complaints_status ON complaints (status);
CREATE INDEX IF NOT EXISTS idx_complaints_created_by ON complaints (created_by);
CREATE INDEX IF NOT EXISTS idx_complaints_assigned_worker ON complaints (assigned_worker);
CREATE INDEX IF NOT EXISTS idx_work_assignments_complaint ON work_assignments (complaint_id);
CREATE INDEX IF NOT EXISTS idx_work_assignments_worker ON work_assignments (worker_id);
