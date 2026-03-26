package egen5208.gym.dto.HealthMetrics;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMetrics {
    @NotNull(message = "Weight is required")
    private Double weight;
    @NotNull(message = "Height is required")
    private Double height;
    @NotNull(message = "Heart rate is required")
    private Integer heartRate;
    @NotNull(message = "Blood pressure is required")
    private String bloodPressure;
    @NotNull(message = "Blood sugar is required")
    private Double bloodSugar;
    private Boolean isDiabetic;
    @NotNull(message = "Allergies is required")
    private String allergies;
    @NotNull(message = "Medical notes is required")
    private String medicalNotes;
}
