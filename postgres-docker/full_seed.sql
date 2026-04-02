-- USERS ARE INSERTED THROUGH POSTMAN 

-- 1. INSERT TRAINERS

INSERT INTO trainers (user_id, specialty, bio)
VALUES 
    (3, 'Bodybuilding & Strength', 'Pro bodybuilder with 15 years coach experience.'),
    (4, 'HIIT & Group Fitness', 'Specializes in high-energy classes and weight loss.');

-- 2. INSERT ROOMS

INSERT INTO rooms (room_number, capacity)
VALUES 
    ('Gym Floor A', 50),
    ('Small Studio', 10),
    ('Large Studio', 25);

-- 3. INSERT TRAINER AVAILABILITY

INSERT INTO trainer_availability (trainer_id, available_date, start_time, end_time, is_available)
VALUES 
    (1, CURRENT_DATE, '08:00:00', '13:00:00', TRUE),
    (1, CURRENT_DATE + INTERVAL '1 day', '08:00:00', '13:00:00', TRUE);

-- Trainer 2 (User 4)
INSERT INTO trainer_availability (trainer_id, available_date, start_time, end_time, is_available)
VALUES 
    (2, CURRENT_DATE, '15:00:00', '20:00:00', TRUE),
    (2, CURRENT_DATE + INTERVAL '1 day', '15:00:00', '20:00:00', TRUE);

-- 4. INSERT EQUIPMENT
INSERT INTO equipment (room_id, name, status, last_maintenance_date, next_maintenance_date)
VALUES 
    (1, 'Squat Rack B2', 'OPERATIONAL', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '60 days'),
    (1, 'Bench Press', 'UNDER_REPAIR', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '60 days'),
    (1, 'Leg Press', 'OUT_OF_SERVICE', CURRENT_DATE - INTERVAL '30 days', CURRENT_DATE + INTERVAL '60 days'),
    (1, 'Smith Machine', 'OPERATIONAL', CURRENT_DATE - INTERVAL '15 days', CURRENT_DATE + INTERVAL '75 days'),
    (2, 'Treadmill', 'UNDER_REPAIR', CURRENT_DATE - INTERVAL '15 days', CURRENT_DATE + INTERVAL '75 days'),
    (3, 'Treadmill', 'OUT_OF_SERVICE', CURRENT_DATE - INTERVAL '15 days', CURRENT_DATE + INTERVAL '75 days');
    
-- 5. INSERT MAINTENANCE LOGS
INSERT INTO maintenance_logs (equipment_id, issue_description, reported_at, resolved_at, technician_notes)
VALUES 
    (1, 'Greased cable pulley.', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP, 'Pulley was sticking; now smooth.');

-- 6. INSERT FITNESS GOALS

INSERT INTO fitness_goals (member_id, goal_description, status, created_at)
VALUES 
    (5, 'Max squat 150kg', 'In Progress', CURRENT_TIMESTAMP - INTERVAL '7 days'),
    (6, 'Run a 5k without stopping', 'Completed', CURRENT_TIMESTAMP - INTERVAL '1 month');

-- 7. INSERT HEALTH METRICS
-- Linked to member_id 5 and 6
INSERT INTO health_metrics (member_id, weight, height, heart_rate, blood_pressure, blood_sugar, is_diabetic, allergies, medical_notes)
VALUES 
    (5, 92.5, 185, 68, '122/82', 5.4, FALSE, 'Latex', 'Athletic build'),
    (6, 60.0, 168, 62, '110/72', 5.1, FALSE, 'None', 'Very active');

-- 8. INSERT CLASSES

INSERT INTO classes (name, description)
VALUES 
    ('Power Lifting 101', 'Mastering the big three lifts.'),
    ('Fat Burning HIIT', 'Quick sessions to burn maximal calories.');

-- 9. INSERT CLASS SCHEDULES
INSERT INTO class_schedules (class_id, trainer_id, room_id, schedule_date, start_time, end_time, max_capacity)
VALUES 
    (1, 1, 1, CURRENT_DATE + INTERVAL '2 days', '10:00:00', '11:30:00', 10);
INSERT INTO class_schedules (class_id, trainer_id, room_id, schedule_date, start_time, end_time, max_capacity)
VALUES 
    (2, 2, 3, CURRENT_DATE + INTERVAL '2 days', '17:00:00', '18:00:00', 25);

-- 10. INSERT CLASS REGISTRATIONS
INSERT INTO class_registrations (schedule_id, member_id, registered_at)
VALUES 
    (2, 5, CURRENT_TIMESTAMP),
    (2, 6, CURRENT_TIMESTAMP);

-- 11. INSERT PERSONAL SESSIONS
INSERT INTO personal_sessions (trainer_id, member_id, room_id, session_date, start_time, end_time)
VALUES 
    (1, 5, 2, CURRENT_DATE + INTERVAL '1 day', '09:00:00', '10:00:00');

-- ==========================================================
-- RESET SEQUENCES (Synchronize auto-increment IDs)
-- ==========================================================
SELECT setval('trainers_trainer_id_seq', (SELECT MAX(trainer_id) FROM trainers));
SELECT setval('rooms_room_id_seq', (SELECT MAX(room_id) FROM rooms));
SELECT setval('equipment_equipment_id_seq', (SELECT MAX(equipment_id) FROM equipment));
SELECT setval('maintenance_logs_log_id_seq', (SELECT MAX(log_id) FROM maintenance_logs));
SELECT setval('fitness_goals_goal_id_seq', (SELECT MAX(goal_id) FROM fitness_goals));
SELECT setval('health_metrics_metric_id_seq', (SELECT MAX(metric_id) FROM health_metrics));
SELECT setval('classes_class_id_seq', (SELECT MAX(class_id) FROM classes));
SELECT setval('class_schedules_schedule_id_seq', (SELECT MAX(schedule_id) FROM class_schedules));
SELECT setval('class_registrations_registration_id_seq', (SELECT MAX(registration_id) FROM class_registrations));
SELECT setval('personal_sessions_session_id_seq', (SELECT MAX(session_id) FROM personal_sessions));
SELECT setval('billings_billing_id_seq', (SELECT MAX(billing_id) FROM billings));
