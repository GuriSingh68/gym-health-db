package egen5208.gym.dto.Equipment;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EquipmentRequestDTO {
    @NotNull(message = "Room ID is required")
    private Long roomId;
    @NotBlank(message = "Equipment name is required")
    private String name;
    @NotBlank(message = "Equipment status is required")
    private String status;
    @NotNull(message = "Last maintenance date is required")
    private LocalDate lastMaintenanceDate;
    @NotNull(message = "Next maintenance date is required")
    private LocalDate nextMaintenanceDate;
}
