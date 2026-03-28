package egen5208.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.TrainerAvailability;

@Repository
public interface TrainerAvailabilityRepo extends JpaRepository<TrainerAvailability, Long> {

}
