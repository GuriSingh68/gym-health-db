package egen5208.gym.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.ClassSchedule;

@Repository
public interface ClassScheduleRepo extends JpaRepository<ClassSchedule, Long> {
    @Query("SELECT cs FROM ClassSchedule cs WHERE cs.room.roomId = :roomId")
    Optional<ClassSchedule> findByRoomId(Long roomId);
}
