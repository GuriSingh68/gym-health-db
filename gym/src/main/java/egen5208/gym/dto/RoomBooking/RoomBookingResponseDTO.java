package egen5208.gym.dto.RoomBooking;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class RoomBookingResponseDTO {
    private String message;
    private Long targetId;
    private Long roomId;
    private String roomName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
