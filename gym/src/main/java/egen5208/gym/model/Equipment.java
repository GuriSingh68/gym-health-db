package egen5208.gym.model;

import java.time.LocalDate;
import java.util.List;

import egen5208.gym.model.enums.EquipmentStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "equipment")
@Data
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private String name;

    @Column(length = 30)
    private String status = "operational";

    private LocalDate lastMaintenanceDate;
    private LocalDate nextMaintenanceDate;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<MaintenanceLog> maintenanceLogs;
}
