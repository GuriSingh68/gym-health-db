package egen5208.gym.dto.FitnessGoal;

import lombok.Data;

@Data
public class FitnessGoalResponseDTO {
    private Long goalId;
    private String email;
    private String name;
    private String goalDescription;
    private String status;
}
