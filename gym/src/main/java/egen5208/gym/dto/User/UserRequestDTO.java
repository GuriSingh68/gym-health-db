package egen5208.gym.dto.User;

import java.time.LocalDate;

import egen5208.gym.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message = "Date of birth is required")
    private LocalDate dob;
    @NotBlank(message = "Gender is required")
    private String gender;
    @NotBlank(message = "Role is required")
    private UserRole role;
}
