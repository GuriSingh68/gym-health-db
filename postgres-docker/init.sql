-- ==========================================================
-- Health and Fitness Club Management System - CLEAN SCHEMA
-- PostgreSQL (Consistent BIGSERIAL + BIGINT)
-- ==========================================================

-- ==========================================
-- 1. TABLES
-- ==========================================


CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    role VARCHAR(20) NOT NULL, -- Changed from BIGINT to VARCHAR
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    dob DATE,
    gender TEXT NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fitness_goals (
                               goal_id BIGSERIAL PRIMARY KEY,
                               member_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                               goal_description TEXT NOT NULL,
                               status TEXT NOT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE health_metrics (
    metric_id BIGSERIAL PRIMARY KEY,
    member_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
    weight NUMERIC(5,2),
    height NUMERIC(5,2),
    heart_rate INT,
    blood_pressure VARCHAR(20),
    blood_sugar NUMERIC(5,2),
    is_diabetic BOOLEAN DEFAULT FALSE,
    allergies TEXT DEFAULT 'None',
    medical_notes TEXT DEFAULT 'None',
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE trainers (
                          trainer_id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                          specialty VARCHAR(100),
                          bio TEXT
);

CREATE TABLE trainer_availability (
                                      availability_id BIGSERIAL PRIMARY KEY,
                                      trainer_id BIGINT NOT NULL REFERENCES trainers(trainer_id) ON DELETE CASCADE,
                                      available_date DATE NOT NULL,
                                      start_time TIME NOT NULL,
                                      end_time TIME NOT NULL,
                                      CONSTRAINT chk_time_order CHECK (end_time > start_time)
);

CREATE TABLE rooms (
                       room_id BIGSERIAL PRIMARY KEY,
                       room_number VARCHAR(20) UNIQUE NOT NULL,
                       capacity INT NOT NULL CHECK (capacity > 0)
);

CREATE TABLE equipment (
                           equipment_id BIGSERIAL PRIMARY KEY,
                           room_id BIGINT REFERENCES rooms(room_id) ON DELETE SET NULL,
                           name VARCHAR(100) NOT NULL,
                           last_maintenance_date DATE
);

CREATE TABLE classes (
                         class_id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description TEXT
);

CREATE TABLE class_schedules (
                                 schedule_id BIGSERIAL PRIMARY KEY,
                                 class_id BIGINT NOT NULL REFERENCES classes(class_id) ON DELETE CASCADE,
                                 trainer_id BIGINT NOT NULL REFERENCES trainers(trainer_id),
                                 room_id BIGINT NOT NULL REFERENCES rooms(room_id),
                                 schedule_date DATE NOT NULL,
                                 start_time TIME NOT NULL,
                                 end_time TIME NOT NULL,
                                 max_capacity INT NOT NULL CHECK (max_capacity > 0),
                                 CONSTRAINT chk_schedule_time CHECK (end_time > start_time)
);

CREATE TABLE class_registrations (
                                     registration_id BIGSERIAL PRIMARY KEY,
                                     schedule_id BIGINT NOT NULL REFERENCES class_schedules(schedule_id) ON DELETE CASCADE,
                                     member_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                                     registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     CONSTRAINT uq_schedule_member UNIQUE(schedule_id, member_id)
);

CREATE TABLE personal_sessions (
                                   session_id BIGSERIAL PRIMARY KEY,
                                   trainer_id BIGINT NOT NULL REFERENCES trainers(trainer_id),
                                   member_id BIGINT NOT NULL REFERENCES users(user_id),
                                   room_id BIGINT REFERENCES rooms(room_id),
                                   session_date DATE NOT NULL,
                                   start_time TIME NOT NULL,
                                   end_time TIME NOT NULL,
                                   CONSTRAINT chk_session_time CHECK (end_time > start_time)
);

CREATE TABLE billings (
                          billing_id BIGSERIAL PRIMARY KEY,
                          member_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                          amount NUMERIC(8, 2) NOT NULL CHECK (amount >= 0),
                          payment_date TIMESTAMP,
                          payment_method VARCHAR(50)
);

-- ==========================================
-- 2. INDEXES
-- ==========================================

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_health_metrics_member ON health_metrics(member_id);
CREATE INDEX idx_class_schedules_date ON class_schedules(schedule_date);

-- ==========================================
-- 3. TRIGGERS
-- ==========================================

CREATE OR REPLACE FUNCTION check_class_capacity_limit()
RETURNS TRIGGER AS $$
DECLARE
current_capacity INT;
    allowed_capacity INT;
BEGIN
SELECT COUNT(*) INTO current_capacity
FROM class_registrations
WHERE schedule_id = NEW.schedule_id;

SELECT max_capacity INTO allowed_capacity
FROM class_schedules
WHERE schedule_id = NEW.schedule_id;

IF current_capacity >= allowed_capacity THEN
        RAISE EXCEPTION 'Registration Failed: The class has reached its maximum capacity of %.', allowed_capacity;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_class_capacity
    BEFORE INSERT ON class_registrations
    FOR EACH ROW EXECUTE FUNCTION check_class_capacity_limit();


CREATE OR REPLACE FUNCTION prevent_member_overlapping_sessions()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM personal_sessions
        WHERE member_id = NEW.member_id
          AND session_date = NEW.session_date
          AND NEW.start_time < end_time
          AND NEW.end_time > start_time
          AND session_id IS DISTINCT FROM NEW.session_id
    ) THEN
        RAISE EXCEPTION 'Scheduling Conflict: Member already has a session during this time.';
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_prevent_member_overlap
    BEFORE INSERT OR UPDATE ON personal_sessions
                         FOR EACH ROW EXECUTE FUNCTION prevent_member_overlapping_sessions();