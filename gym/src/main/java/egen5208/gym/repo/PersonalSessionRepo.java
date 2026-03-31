package egen5208.gym.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.PersonalSessionBooking;

@Repository
public interface PersonalSessionRepo extends JpaRepository<PersonalSessionBooking, Long> {
    @Query("SELECT ps FROM PersonalSessionBooking ps WHERE ps.room.roomId = :roomId")
    Optional<PersonalSessionBooking> findByRoomId(Long roomId);
}
