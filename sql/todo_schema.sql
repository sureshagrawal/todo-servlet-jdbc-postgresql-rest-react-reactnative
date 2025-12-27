-- =====================================================
-- Project  : ToDo Application
-- Backend  : Servlet + JDBC
-- Database : PostgreSQL
-- =====================================================

-- -----------------------------------------------------
-- STEP 1: Create Database
-- NOTE:
-- Run this command ONLY ONCE.
-- If database already exists, skip this step.
-- -----------------------------------------------------

CREATE DATABASE todo_db;

-- -----------------------------------------------------
-- STEP 2: Connect to Database
-- (Required when using psql)
-- -----------------------------------------------------
\c todo_db

-- -----------------------------------------------------
-- STEP 3: Create todo table
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS todo (
    id SERIAL PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    description TEXT,

    priority VARCHAR(10) NOT NULL,
    status   VARCHAR(10) NOT NULL,

    due_date DATE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------------------------
-- STEP 4: Optional sample data for testing
-- -----------------------------------------------------
INSERT INTO todo (title, description, priority, status, due_date)
VALUES
('Learn Servlet', 'Build REST API using Servlet and JDBC', 'HIGH', 'PENDING', '2025-01-10'),
('Learn React', 'Create frontend using React and Tailwind', 'MEDIUM', 'PENDING', '2025-01-15');
