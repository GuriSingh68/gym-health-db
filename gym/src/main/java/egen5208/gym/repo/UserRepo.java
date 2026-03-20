package egen5208.gym.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.User;
import jakarta.validation.constraints.NotBlank;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByEmail(@NotBlank(message = "Email is required") String email);
}
