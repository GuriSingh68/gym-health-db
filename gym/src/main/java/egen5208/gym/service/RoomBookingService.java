package egen5208.gym.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egen5208.gym.dto.RoomBooking.RoomBookingDTO;
import egen5208.gym.dto.RoomBooking.RoomBookingResponseDTO;
import egen5208.gym.exception.RoomNotFoundException;
import egen5208.gym.model.ClassSchedule;
import egen5208.gym.model.PersonalSessionBooking;
import egen5208.gym.model.Room;
import egen5208.gym.repo.ClassScheduleRepo;
import egen5208.gym.repo.PersonalSessionRepo;
import egen5208.gym.repo.RoomRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomBookingService {
        private final RoomRepo roomRepo;
        private final ClassScheduleRepo classScheduleRepo;
        private final PersonalSessionRepo personalSessionRepo;
        private static final Logger log = LoggerFactory.getLogger(RoomBookingService.class);

        @Transactional
        public RoomBookingResponseDTO bookRoomPrivateSession(RoomBookingDTO dto) {
                RoomBookingResponseDTO response = new RoomBookingResponseDTO();

                boolean isOccupied = roomRepo.isRoomOccupied(
                                dto.getCurrentRoomId(), dto.getDate(), dto.getStartTime(), dto.getEndTime());

                if (isOccupied) {
                        response.setMessage("Conflict: Room is already booked for this time slot.");
                        response.setTargetId(dto.getTargetRoomId());
                        response.setRoomId(dto.getCurrentRoomId());
                        response.setDate(dto.getDate());
                        response.setStartTime(dto.getStartTime());
                        response.setEndTime(dto.getEndTime());
                        return response;
                }

                Room room = roomRepo.findById(dto.getTargetRoomId())
                                .orElseThrow(() -> new RoomNotFoundException("Target room doesn't exist."));
                if (dto.getSessionId() != null) {
                        PersonalSessionBooking ps = personalSessionRepo.findById(dto.getSessionId())
                                        .orElseThrow(() -> new RoomNotFoundException(
                                                        "There is not current room with that room id."));
                        ps.setRoom(room);
                        personalSessionRepo.save(ps);
                }

                response.setMessage("Room " + room.getRoomId() + " successfully assigned to session.");
                return response;
        }

        @Transactional
        public RoomBookingResponseDTO bookRoomClass(RoomBookingDTO roomBookingDTO) {
                RoomBookingResponseDTO response = new RoomBookingResponseDTO();

                boolean isOccupied = roomRepo.isRoomOccupied(
                                roomBookingDTO.getCurrentRoomId(), roomBookingDTO.getDate(),
                                roomBookingDTO.getStartTime(),
                                roomBookingDTO.getEndTime());
                log.info("Room is occupied: " + isOccupied);
                if (isOccupied) {
                        response.setMessage("Conflict: Room is already booked for this time slot.");
                        return response;
                }

                Room room = roomRepo.findById(roomBookingDTO.getCurrentRoomId())
                                .orElseThrow(() -> new RoomNotFoundException("Target room doesn't exist."));
                if (roomBookingDTO.getScheduleId() != null) {
                        ClassSchedule cs = classScheduleRepo.findById(roomBookingDTO.getScheduleId())
                                        .orElseThrow(() -> new RoomNotFoundException(
                                                        "There is not current room with that room id."));
                        cs.setRoom(room);
                        classScheduleRepo.save(cs);
                }

                response.setMessage("Room " + room.getRoomId() + " successfully assigned to class schedule.");
                return response;
        }

}
