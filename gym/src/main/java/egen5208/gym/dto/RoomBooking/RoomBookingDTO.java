package egen5208.gym.dto.RoomBooking;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookingDTO {
    private Long scheduleId;
    private Long sessionId;
    private Long currentRoomId;
    private Long targetRoomId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
