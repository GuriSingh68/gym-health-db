package egen5208.gym.model;

import java.time.LocalDate;
import java.time.LocalTime;

public interface GroupClassSessionView {
    String getActivityType();

    Long getMemberId();

    String getClassName();

    LocalDate getDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    String getTrainerName();

    String getRoom();
}