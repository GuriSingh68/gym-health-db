package egen5208.gym.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import egen5208.gym.dto.Equipment.EquipmentRequestDTO;
import egen5208.gym.dto.Equipment.EquipmentResponseDTO;
import egen5208.gym.dto.Equipment.MaintenanceLogDTO;
import egen5208.gym.dto.Equipment.UpdateEquipmentStatusDTO;
import egen5208.gym.exception.RoomNotFoundException;
import egen5208.gym.model.Equipment;
import egen5208.gym.model.MaintenanceLog;
import egen5208.gym.model.Room;
import egen5208.gym.model.enums.EquipmentStatus;
import egen5208.gym.repo.EquipmentRepo;
import egen5208.gym.repo.MaintenaceLogRepo;
import egen5208.gym.repo.RoomRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipmentService {
        private final EquipmentRepo equipmentRepo;
        private final MaintenaceLogRepo maintenaceLogRepo;
        private final RoomRepo roomRepo;

        public EquipmentResponseDTO createEquipment(EquipmentRequestDTO equipmentRequestDTO) {
                Room room = roomRepo.findById(equipmentRequestDTO.getRoomId())
                                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
                Equipment equipment = new Equipment();
                equipment.setName(equipmentRequestDTO.getName());
                equipment.setStatus(equipmentRequestDTO.getStatus() != null ? equipmentRequestDTO.getStatus()
                                : "operational");
                equipment.setRoom(room);
                equipment.setLastMaintenanceDate(equipmentRequestDTO.getLastMaintenanceDate());
                equipment.setNextMaintenanceDate(equipmentRequestDTO.getNextMaintenanceDate());
                Equipment createdEquipment = equipmentRepo.save(equipment);
                EquipmentResponseDTO equipmentResponseDTO = new EquipmentResponseDTO();
                equipmentResponseDTO.setEquipmentId(createdEquipment.getEquipmentId());
                equipmentResponseDTO.setName(createdEquipment.getName());
                equipmentResponseDTO.setStatus(createdEquipment.getStatus());
                equipmentResponseDTO.setRoomNumber(createdEquipment.getRoom().getRoomNumber());
                equipmentResponseDTO.setLastMaintenanceDate(createdEquipment.getLastMaintenanceDate().toString());
                equipmentResponseDTO.setNextMaintenanceDate(createdEquipment.getNextMaintenanceDate().toString());
                return equipmentResponseDTO;
        }

        public EquipmentResponseDTO createMaintenanceLog(Long equipmentId,
                        MaintenanceLogDTO maintenanceLogRequestDTO) {
                Equipment equipment = equipmentRepo.findById(equipmentId)
                                .orElseThrow(() -> new RoomNotFoundException("Equipment not found"));

                MaintenanceLog maintenanceLog = new MaintenanceLog();
                maintenanceLog.setEquipment(equipment);
                maintenanceLog.setReportedAt(maintenanceLogRequestDTO.getReportedAt());
                maintenanceLog.setResolvedAt(maintenanceLogRequestDTO.getResolvedAt());
                maintenanceLog.setIssueDescription(maintenanceLogRequestDTO.getIssueDescription());
                if (maintenanceLogRequestDTO.getTechnicianNotes() != null) {
                        maintenanceLog.setTechnician_notes(maintenanceLogRequestDTO.getTechnicianNotes());
                }
                MaintenanceLog createdMaintenanceLog = maintenaceLogRepo.save(maintenanceLog);
                EquipmentResponseDTO equipmentResponseDTO = new EquipmentResponseDTO();
                equipmentResponseDTO.setEquipmentId(createdMaintenanceLog.getEquipment().getEquipmentId());
                equipmentResponseDTO.setName(createdMaintenanceLog.getEquipment().getName());
                equipmentResponseDTO.setStatus(createdMaintenanceLog.getEquipment().getStatus());
                equipmentResponseDTO.setRoomNumber(createdMaintenanceLog.getEquipment().getRoom().getRoomNumber());
                equipmentResponseDTO
                                .setLastMaintenanceDate(createdMaintenanceLog.getEquipment().getLastMaintenanceDate()
                                                .toString());
                equipmentResponseDTO
                                .setNextMaintenanceDate(createdMaintenanceLog.getEquipment().getNextMaintenanceDate()
                                                .toString());
                equipment.setStatus(EquipmentStatus.MAINTENANCE.toString());
                equipmentRepo.save(equipment);
                return equipmentResponseDTO;
        }

        public List<EquipmentResponseDTO> getAllEquipment() {
                List<Equipment> allEquipment = equipmentRepo.findAll();
                List<EquipmentResponseDTO> equipmentResponseDTOs = new ArrayList<>();
                for (Equipment equipment : allEquipment) {
                        EquipmentResponseDTO equipmentResponseDTO = new EquipmentResponseDTO();
                        equipmentResponseDTO.setEquipmentId(equipment.getEquipmentId());
                        equipmentResponseDTO.setName(equipment.getName());
                        equipmentResponseDTO.setStatus(equipment.getStatus());
                        equipmentResponseDTO.setRoomNumber(equipment.getRoom().getRoomNumber());
                        equipmentResponseDTO.setLastMaintenanceDate(equipment.getLastMaintenanceDate().toString());
                        equipmentResponseDTO.setNextMaintenanceDate(equipment.getNextMaintenanceDate().toString());
                        equipmentResponseDTOs.add(equipmentResponseDTO);
                }
                return equipmentResponseDTOs;
        }

        public List<EquipmentResponseDTO> getAllEquipmentByStatus(String status) {
                List<Equipment> allEquipment = equipmentRepo.findByStatus(status);
                List<EquipmentResponseDTO> equipmentResponseDTOs = new ArrayList<>();
                for (Equipment equipment : allEquipment) {
                        EquipmentResponseDTO equipmentResponseDTO = new EquipmentResponseDTO();
                        equipmentResponseDTO.setEquipmentId(equipment.getEquipmentId());
                        equipmentResponseDTO.setName(equipment.getName());
                        equipmentResponseDTO.setStatus(equipment.getStatus());
                        equipmentResponseDTO.setRoomNumber(equipment.getRoom().getRoomNumber());
                        equipmentResponseDTO.setLastMaintenanceDate(equipment.getLastMaintenanceDate().toString());
                        equipmentResponseDTO.setNextMaintenanceDate(equipment.getNextMaintenanceDate().toString());
                        equipmentResponseDTOs.add(equipmentResponseDTO);
                }
                return equipmentResponseDTOs;
        }

        public void updateEquipment(Long equipmentId,
                        UpdateEquipmentStatusDTO updateEquipmentStatusDTO) {
                if (updateEquipmentStatusDTO.getStatus() == null || updateEquipmentStatusDTO.getStatus().isEmpty()) {
                        throw new IllegalArgumentException("Status cannot be null or empty");
                }

                Equipment equipment = equipmentRepo.findById(equipmentId)
                                .orElseThrow(() -> new RoomNotFoundException("Equipment not found"));
                equipment.setStatus(updateEquipmentStatusDTO.getStatus());

                equipmentRepo.save(equipment);
        }
}
