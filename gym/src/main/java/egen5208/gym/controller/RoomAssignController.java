package egen5208.gym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egen5208.gym.dto.RoomBooking.RoomBookingDTO;
import egen5208.gym.dto.RoomBooking.RoomBookingResponseDTO;
import egen5208.gym.service.RoomBookingService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/room")
@AllArgsConstructor
public class RoomAssignController {
    private final RoomBookingService roomBookingService;

    @PatchMapping("/assign-private-session")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignPrivateSession(@RequestBody RoomBookingDTO dto) {
        RoomBookingResponseDTO response = roomBookingService.bookRoomPrivateSession(dto);
        return ResponseEntity.ok(response.getMessage());
    }

    @PatchMapping("/assign-class")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignClass(@RequestBody RoomBookingDTO dto) {
        RoomBookingResponseDTO response = roomBookingService.bookRoomClass(dto);
        return ResponseEntity.ok(response.getMessage());
    }
}
