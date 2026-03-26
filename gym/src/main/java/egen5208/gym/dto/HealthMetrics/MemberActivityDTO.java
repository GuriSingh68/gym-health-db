package egen5208.gym.dto.HealthMetrics;

import java.time.LocalDate;
import java.time.LocalTime;

public interface MemberActivityDTO {
    String getActivityType();
    String getActivityName();
    LocalDate getActivityDate();
    LocalTime getStartTime();
    LocalTime getEndTime();
    String getTrainerName();
}
