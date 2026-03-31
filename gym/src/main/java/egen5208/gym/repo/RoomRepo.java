package egen5208.gym.repo;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.Room;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    @Query(value = """
            SELECT EXISTS (
                SELECT 1 FROM (
                    SELECT room_id, start_time, end_time, schedule_date AS date
                    FROM class_schedules
                    WHERE room_id = :roomId AND schedule_date = :date

                    UNION ALL

                    SELECT room_id, start_time, end_time, session_date AS date
                    FROM personal_sessions
                    WHERE room_id = :roomId AND session_date = :date
                ) AS bookings
                WHERE start_time < :endTime AND end_time > :startTime
            )
            """, nativeQuery = true)
    boolean isRoomOccupied(
            @Param("roomId") Long roomId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);
}
