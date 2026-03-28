package egen5208.gym.dto.Trainer;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TrainerAvailabilityRequestDTO {
    @NotNull(message = "Available date is required")
    private String availableDate;
    @NotNull(message = "Start time is required")
    private String startTime;
    @NotNull(message = "End time is required")
    private String endTime;

    private Long trainerId;

    public boolean isTimeRangeValid() {
        return endTime != null && startTime != null && endTime.compareTo(startTime) > 0;
    }
}
