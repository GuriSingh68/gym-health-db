package egen5208.gym.dto.HealthMetrics;

import java.time.LocalDateTime;

public interface MemberDashboardDTO {
    double getWeight();
    int getHeartRate();
    boolean getIsDiabetic();
    String getAllergies();
    double getBloodSugar();
    String getMedicalNotes();
    String getGoalDescription();
    String getStatus();
    LocalDateTime getRecordedAt();
}
