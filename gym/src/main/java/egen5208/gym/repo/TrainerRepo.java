package egen5208.gym.repo;

import egen5208.gym.model.Trainer;
import egen5208.gym.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByUser(User user);
}
