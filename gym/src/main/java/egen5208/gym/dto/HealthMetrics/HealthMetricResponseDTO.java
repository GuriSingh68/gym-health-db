package egen5208.gym.dto.HealthMetrics;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HealthMetricResponseDTO {
    private Double weight;
    private Double height;
    private Integer heartRate;
    private String bloodPressure;
    private Double bloodSugar;
    private Boolean isDiabetic;
    private String allergies;
    private String medicalNotes;
    private LocalDateTime recordedAt;
}