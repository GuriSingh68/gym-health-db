package egen5208.gym.dto.Trainer;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityPatchRequestDTO {
    private LocalDate availableDate;
    private String startTime;
    private String endTime;
    private Boolean isAvailable;
}
