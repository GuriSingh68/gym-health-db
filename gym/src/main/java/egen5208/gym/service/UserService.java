package egen5208.gym.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import egen5208.gym.dto.User.UserRequestDTO;
import egen5208.gym.dto.User.UserUpdateDTO;
import egen5208.gym.exception.NotAdminException;
import egen5208.gym.exception.UserAlreadyExistsException;
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
            throw new UserAlreadyExistsException("User with email " + userRequestDTO.getEmail() + " already exists");
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
        user.setRegisteredAt(LocalDateTime.now());
        User savedUser = userRepo.save(user);
        return savedUser;
    }

    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if (userUpdateDTO.getFirstName() != null) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            user.setLastName(userUpdateDTO.getLastName());
        }
        if (userUpdateDTO.getPhone() != null) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getDob() != null) {
            user.setDob(userUpdateDTO.getDob());
        }
        if (userUpdateDTO.getGender() != null) {
            user.setGender(userUpdateDTO.getGender());
        }
        if (userUpdateDTO.getRole() != null) {
            if (user.getRole() != UserRole.ADMIN) {
                throw new NotAdminException("You are not authorized to update user role");
            }
            user.setRole(userUpdateDTO.getRole());
        }

        return userRepo.save(user);
    }
}
