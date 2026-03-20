package egen5208.gym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egen5208.gym.dto.User.UserRequestDTO;
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

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User updatedUser = userService.updateUser(userRequestDTO);
        return ResponseEntity.ok("User updated successfully with Email: " + updatedUser.getEmail());
    }

}
