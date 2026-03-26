package egen5208.gym.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_metrics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    private Double weight;
    private Double height;
    private Integer heartRate;
    private String bloodPressure;
    private Double bloodSugar;
    private Boolean isDiabetic;
    private String allergies;
    private String medicalNotes;

    @Column(updatable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() {
        recordedAt = LocalDateTime.now();
    }
}
