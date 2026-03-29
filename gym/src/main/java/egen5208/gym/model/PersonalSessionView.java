package egen5208.gym.model;

import java.time.LocalDate;
import java.time.LocalTime;

public interface PersonalSessionView {
    Long getMemberId();

    LocalDate getSessionDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    String getClientName();

    String getRoomNumber();
}
