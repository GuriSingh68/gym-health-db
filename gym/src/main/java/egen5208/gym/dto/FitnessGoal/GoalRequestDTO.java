package egen5208.gym.dto.FitnessGoal;

import lombok.Data;

@Data
public class GoalRequestDTO {
    private Long userId;
    private String goalDescription;
    private String status;
}
