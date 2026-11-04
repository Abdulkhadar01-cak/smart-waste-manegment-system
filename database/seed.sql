-- =============================================================================
-- SMART WASTE MANAGEMENT SYSTEM - SEED DATA (DML)
-- File: database/seed.sql
-- 
-- Description:
-- Initial demo data for development and testing.
-- Includes test users (Admin, Driver, Citizen), sample smart bins, trucks,
-- historical logs, and demo alerts.
-- =============================================================================

-- 1. Insert Initial System Users
-- Passwords in production should be BCrypt hashed. For development preview:
INSERT INTO users (username, email, password_hash, first_name, last_name, role, phone_number) VALUES
('admin', 'admin@smartwaste.local', '$2a$12$e8UvV2rX6zB2K9aR3vC.uek6Zk3U5g9Hk0P0nQ6Z6c3H1r1s6y9G', 'Eleanor', 'Vance', 'ADMIN', '+1-555-0100'),
('driver_john', 'john.driver@smartwaste.local', '$2a$12$e8UvV2rX6zB2K9aR3vC.uek6Zk3U5g9Hk0P0nQ6Z6c3H1r1s6y9G', 'John', 'Miller', 'DRIVER', '+1-555-0101'),
('driver_sarah', 'sarah.driver@smartwaste.local', '$2a$12$e8UvV2rX6zB2K9aR3vC.uek6Zk3U5g9Hk0P0nQ6Z6c3H1r1s6y9G', 'Sarah', 'Connor', 'DRIVER', '+1-555-0102'),
('citizen_demo', 'citizen@smartwaste.local', '$2a$12$e8UvV2rX6zB2K9aR3vC.uek6Zk3U5g9Hk0P0nQ6Z6c3H1r1s6y9G', 'Alex', 'Rivera', 'CITIZEN', '+1-555-0103');

-- 2. Insert Smart Waste Bins across city zones
INSERT INTO waste_bins (bin_code, waste_type, capacity_liters, fill_level_percentage, battery_percentage, status, latitude, longitude, address, zone_area) VALUES
('BIN-DT-001', 'GENERAL', 240.0, 88.5, 94.0, 'FULL', 40.7128, -74.0060, '120 Broadway St, Downtown', 'Zone 1 - Downtown'),
('BIN-DT-002', 'RECYCLABLE', 240.0, 42.0, 89.0, 'ACTIVE', 40.7135, -74.0075, '150 Broadway St, Downtown', 'Zone 1 - Downtown'),
('BIN-DT-003', 'ORGANIC', 120.0, 15.0, 98.0, 'ACTIVE', 40.7142, -74.0050, 'Market Square, Downtown', 'Zone 1 - Downtown'),
('BIN-UP-101', 'GENERAL', 360.0, 92.0, 78.0, 'FULL', 40.7829, -73.9654, 'Central Park West & 72nd', 'Zone 2 - Uptown'),
('BIN-UP-102', 'RECYCLABLE', 240.0, 65.0, 85.0, 'ACTIVE', 40.7850, -73.9680, 'Museum Mile Entrance', 'Zone 2 - Uptown'),
('BIN-ID-201', 'HAZARDOUS', 120.0, 10.0, 99.0, 'ACTIVE', 40.7500, -73.9900, 'Industrial Park Gate 4', 'Zone 3 - Industrial');

-- 3. Insert Collection Trucks
INSERT INTO collection_trucks (license_plate, model, capacity_kg, driver_id, status, current_latitude, current_longitude) VALUES
('TRK-NY-101', 'Volvo FE Electric 27t', 5000.00, 2, 'ON_ROUTE', 40.7130, -74.0062),
('TRK-NY-102', 'Mercedes Econic Eco', 7500.00, 3, 'IDLE', 40.7000, -74.0100);

-- 4. Insert Initial Collection Logs
INSERT INTO collection_logs (bin_id, truck_id, collected_by, fill_level_before, weight_collected_kg, notes) VALUES
(1, 1, 2, 95.0, 68.50, 'Routine morning pickup completed on schedule.'),
(2, 1, 2, 85.0, 45.20, 'Bin cleaned and sensor recalibrated.');

-- 5. Insert Sample Alerts
INSERT INTO alerts (bin_id, alert_type, severity, message, is_resolved) VALUES
(1, 'OVERFLOW_WARNING', 'HIGH', 'Bin BIN-DT-001 has exceeded 85% capacity (Current: 88.5%). Immediate collection recommended.', FALSE),
(4, 'OVERFLOW_WARNING', 'CRITICAL', 'Bin BIN-UP-101 has reached 92% capacity. Risk of overflow.', FALSE);
