package egen5208.gym.dto.Equipment;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceLogDTO {
    @NotBlank(message = "Issue description is required")
    private String issueDescription;
    @NotNull(message = "Reported at is required")
    @Builder.Default
    private LocalDateTime reportedAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;
    private String technicianNotes;
}
