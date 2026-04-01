package egen5208.gym.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "maintenance_logs")
@Data
public class MaintenanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String issueDescription;

    private LocalDateTime reportedAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;

    @Column(columnDefinition = "TEXT")
    private String technician_notes;
}