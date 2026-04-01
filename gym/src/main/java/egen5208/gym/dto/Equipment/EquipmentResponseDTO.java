package egen5208.gym.dto.Equipment;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquipmentResponseDTO {
    private Long equipmentId;
    private String name;
    private String status;
    private String roomNumber;
    private String lastMaintenanceDate;
    private String nextMaintenanceDate;
}
