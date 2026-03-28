package egen5208.gym.dto.Trainer;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityPatchRequestDTO {
    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isAvailable;
}
