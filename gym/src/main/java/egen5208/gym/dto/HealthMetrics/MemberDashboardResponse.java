package egen5208.gym.dto.HealthMetrics;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDashboardResponse {
    private List<MemberDashboardDTO> healthMetrics;
    private List<MemberActivityDTO> upcomingActivities;
}
