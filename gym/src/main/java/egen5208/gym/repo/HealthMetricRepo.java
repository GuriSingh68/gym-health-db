package egen5208.gym.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import egen5208.gym.dto.HealthMetrics.MemberActivityDTO;
import egen5208.gym.dto.HealthMetrics.MemberDashboardDTO;
import egen5208.gym.model.HealthMetric;

@Repository
public interface HealthMetricRepo extends JpaRepository<HealthMetric, Long> {
    List<HealthMetric> findByMemberUserId(Long userId);

    List<HealthMetric> findByMemberUserIdOrderByRecordedAtDesc(Long userId);

    @Query(value = """
            SELECT
                lh.weight AS weight,
                lh.heart_rate AS heartRate,
                lh.is_diabetic AS isDiabetic,
                lh.allergies AS allergies,
                lh.blood_sugar AS bloodSugar,
                lh.medical_notes AS medicalNotes,
                fg.goal_description AS goalDescription,
                fg.status AS status,
                lh.recorded_at AS recordedAt
            FROM fitness_goals fg
            JOIN (
                SELECT * FROM health_metrics
                WHERE member_id = :memberId
                ORDER BY recorded_at DESC
                LIMIT 1
            ) AS lh ON fg.member_id = lh.member_id
            """, nativeQuery = true)
    List<MemberDashboardDTO> getDashboardData(Long memberId);

    @Query(value = """
            SELECT
                'Group Class' AS activityType,
                c.name AS activityName,
                cs.schedule_date AS activityDate,
                cs.start_time AS startTime,
                cs.end_time AS endTime,
                (us.first_name || ' ' || us.last_name) AS trainerName
            FROM class_registrations cr
            JOIN class_schedules cs ON cr.schedule_id = cs.schedule_id
            JOIN classes c ON cs.class_id = c.class_id
            JOIN trainers t ON cs.trainer_id = t.trainer_id
            JOIN users us ON t.user_id = us.user_id
            WHERE cr.member_id = :memberId

            UNION ALL

            SELECT
                'Personal Session' AS activityType,
                'One-on-One Session' AS activityName,
                ps.session_date AS activityDate,
                ps.start_time AS startTime,
                ps.end_time AS endTime,
                (us.first_name || ' ' || us.last_name) AS trainerName
            FROM personal_sessions ps
            JOIN trainers t ON ps.trainer_id = t.trainer_id
            JOIN users us ON t.user_id = us.user_id
            WHERE ps.member_id = :memberId

            ORDER BY activityDate DESC, startTime DESC
            """, nativeQuery = true)
    List<MemberActivityDTO> getUpcomingActivities(Long memberId);
}
