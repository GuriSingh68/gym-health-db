package egen5208.gym.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import egen5208.gym.dto.User.UserRequestDTO;
import egen5208.gym.model.User;
import egen5208.gym.model.enums.UserRole;
import egen5208.gym.repo.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDTO userRequestDTO) {
        if (userRepo.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + userRequestDTO.getEmail() + " already exists");
        }
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setPhone(userRequestDTO.getPhone());
        user.setDob(userRequestDTO.getDob());
        user.setGender(userRequestDTO.getGender());
        user.setRole(userRequestDTO.getRole());
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    public User updateUser(UserRequestDTO userRequestDTO) {
        User user = userRepo.findByEmail(userRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userRequestDTO.getEmail()));

        if (userRequestDTO.getFirstName() != null) {
            user.setFirstName(userRequestDTO.getFirstName());
        }
        if (userRequestDTO.getLastName() != null) {
            user.setLastName(userRequestDTO.getLastName());
        }
        if (userRequestDTO.getPhone() != null) {
            user.setPhone(userRequestDTO.getPhone());
        }
        if (userRequestDTO.getDob() != null) {
            user.setDob(userRequestDTO.getDob());
        }
        if (userRequestDTO.getGender() != null) {
            user.setGender(userRequestDTO.getGender());
        }
        if (userRequestDTO.getRole() != null || user.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("You are not authorized to update user role");
        }

        return userRepo.save(user);
    }
}
