package egen5208.gym.dto.Trainer;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerRespnse {
    private Long availabilityId;
    private Long trainerId;
    private String trainerName;
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
