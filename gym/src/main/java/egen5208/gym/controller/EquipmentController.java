package egen5208.gym.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egen5208.gym.dto.Equipment.EquipmentRequestDTO;
import egen5208.gym.dto.Equipment.EquipmentResponseDTO;
import egen5208.gym.dto.Equipment.MaintenanceLogDTO;
import egen5208.gym.dto.Equipment.UpdateEquipmentStatusDTO;
import egen5208.gym.service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EquipmentResponseDTO> createEquipment(
            @Valid @RequestBody EquipmentRequestDTO equipmentRequestDTO) {
        EquipmentResponseDTO createdEquipment = equipmentService.createEquipment(equipmentRequestDTO);
        return ResponseEntity.ok(createdEquipment);
    }

    @PostMapping("/maintenance/{equipmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EquipmentResponseDTO> createMaintenanceLog(
            @PathVariable Long equipmentId, @Valid @RequestBody MaintenanceLogDTO maintenanceLogRequestDTO) {
        EquipmentResponseDTO createdMaintenanceLog = equipmentService
                .createMaintenanceLog(equipmentId, maintenanceLogRequestDTO);
        return ResponseEntity.ok(createdMaintenanceLog);
    }

    @GetMapping()
    public ResponseEntity<List<EquipmentResponseDTO>> getAllEquipment() {
        List<EquipmentResponseDTO> allEquipment = equipmentService.getAllEquipment();
        return ResponseEntity.ok(allEquipment);
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EquipmentResponseDTO>> getAllEquipmentByStatus(@RequestParam String status) {
        List<EquipmentResponseDTO> allEquipment = equipmentService.getAllEquipmentByStatus(status);
        return ResponseEntity.ok(allEquipment);
    }

    @PatchMapping("/{equipmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateEquipment(@PathVariable Long equipmentId,
            @Valid @RequestBody UpdateEquipmentStatusDTO updateEquipmentStatusDTO) {
        equipmentService.updateEquipment(equipmentId, updateEquipmentStatusDTO);
        return ResponseEntity.ok("Equipment status updated successfully with id: " + equipmentId + " and status: "
                + updateEquipmentStatusDTO.getStatus());
    }
}
