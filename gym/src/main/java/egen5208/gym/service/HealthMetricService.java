package egen5208.gym.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import egen5208.gym.dto.HealthMetrics.CreateMetrics;
import egen5208.gym.dto.HealthMetrics.HealthMetricResponseDTO;
import egen5208.gym.dto.HealthMetrics.MemberActivityDTO;
import egen5208.gym.dto.HealthMetrics.MemberDashboardDTO;
import egen5208.gym.dto.HealthMetrics.MemberDashboardResponse;
import egen5208.gym.dto.HealthMetrics.MetricResponse;
import egen5208.gym.exception.NoRecordsFoundException;
import egen5208.gym.exception.UserNotfoundException;
import egen5208.gym.model.HealthMetric;
import egen5208.gym.model.User;
import egen5208.gym.repo.HealthMetricRepo;
import egen5208.gym.repo.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthMetricService {

        private final HealthMetricRepo healthMetricRepo;
        private final UserRepo userRepo;

        public MetricResponse createHealthRecord(CreateMetrics request, String email) {
                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UserNotfoundException("User not found"));
                HealthMetric healthMetric = new HealthMetric();
                healthMetric.setMember(user);
                healthMetric.setWeight(request.getWeight());
                healthMetric.setHeight(request.getHeight());
                healthMetric.setHeartRate(request.getHeartRate());
                healthMetric.setBloodPressure(request.getBloodPressure());
                healthMetric.setBloodSugar(request.getBloodSugar());
                healthMetric.setIsDiabetic(request.getIsDiabetic());
                healthMetric.setAllergies(request.getAllergies());
                healthMetric.setMedicalNotes(request.getMedicalNotes());
                healthMetricRepo.save(healthMetric);
                MetricResponse response = new MetricResponse();
                response.setName(
                                healthMetric.getMember().getFirstName() + " " + healthMetric.getMember().getLastName());
                response.setMessage("Health record created successfully for " + healthMetric.getMember().getFirstName()
                                + " "
                                + healthMetric.getMember().getLastName());
                return response;
        }

        public List<HealthMetricResponseDTO> getHealthRecord(String email) {
                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UserNotfoundException("User not found"));
                List<HealthMetric> healthMetrics = healthMetricRepo
                                .findByMemberUserIdOrderByRecordedAtDesc(user.getUserId());
                if (healthMetrics.isEmpty()) {
                        throw new NoRecordsFoundException(
                                        "No health records found for user " + user.getFirstName() + " "
                                                        + user.getLastName());
                }

                return healthMetrics.stream().map(healthMetric -> {
                        HealthMetricResponseDTO response = new HealthMetricResponseDTO();
                        response.setWeight(healthMetric.getWeight());
                        response.setHeight(healthMetric.getHeight());
                        response.setHeartRate(healthMetric.getHeartRate());
                        response.setBloodPressure(healthMetric.getBloodPressure());
                        response.setBloodSugar(healthMetric.getBloodSugar());
                        response.setIsDiabetic(healthMetric.getIsDiabetic());
                        response.setAllergies(healthMetric.getAllergies());
                        response.setMedicalNotes(healthMetric.getMedicalNotes());
                        response.setRecordedAt(healthMetric.getRecordedAt());
                        return response;
                }).collect(Collectors.toList());
        }

        public MemberDashboardResponse getDashboard(String email) {

                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UserNotfoundException("User not found"));
                
                List<MemberDashboardDTO> healthMetrics = healthMetricRepo.getDashboardData(user.getUserId());
                List<MemberActivityDTO> activities = healthMetricRepo.getUpcomingActivities(user.getUserId());

                if (healthMetrics.isEmpty() && activities.isEmpty()) {
                        throw new NoRecordsFoundException(
                                        "No health records or activities found for user " + user.getFirstName() + " "
                                                        + user.getLastName());
                }

                return MemberDashboardResponse.builder()
                                .healthMetrics(healthMetrics)
                                .upcomingActivities(activities)
                                .build();
        }
}