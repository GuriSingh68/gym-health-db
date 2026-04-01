package egen5208.gym.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.Equipment;

@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, Long> {
    List<Equipment> findByStatus(String status);
}
