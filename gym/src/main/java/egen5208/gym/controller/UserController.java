package egen5208.gym.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egen5208.gym.dto.User.UserRequestDTO;
import egen5208.gym.dto.User.UserUpdateDTO;
import egen5208.gym.model.User;
import egen5208.gym.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User createdUser = userService.createUser(userRequestDTO);
        return ResponseEntity.ok("User " + createdUser.getFirstName() + " " + createdUser.getLastName() +
                " created successfully with Email: " + createdUser.getEmail());
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        User updatedUser = userService.updateUser(id, userUpdateDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("email", updatedUser.getEmail());

        return ResponseEntity.ok(response);
    }

}
