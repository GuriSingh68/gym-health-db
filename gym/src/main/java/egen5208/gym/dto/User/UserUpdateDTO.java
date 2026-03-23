package egen5208.gym.dto.User;

import java.time.LocalDate;

import egen5208.gym.model.enums.UserRole;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private LocalDate dob;
    private UserRole role;
}
