package egen5208.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.GymClasses;

@Repository
public interface GymClassesRepo extends JpaRepository<GymClasses, Long> {

}
