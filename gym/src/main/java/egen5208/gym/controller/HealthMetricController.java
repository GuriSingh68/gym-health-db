package egen5208.gym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import egen5208.gym.dto.HealthMetrics.CreateMetrics;
import egen5208.gym.dto.HealthMetrics.HealthMetricResponseDTO;
import egen5208.gym.dto.HealthMetrics.MemberDashboardResponse;
import egen5208.gym.dto.HealthMetrics.MetricResponse;
import egen5208.gym.service.HealthMetricService;
import egen5208.gym.utils.util;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/health-metrics")
@RequiredArgsConstructor
public class HealthMetricController {

    private final HealthMetricService healthMetricService;

    @PostMapping("")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<Map<String, String>> createHealthRecord(@Valid @RequestBody CreateMetrics request) {
        String email = util.getEmail();
        MetricResponse result = healthMetricService.createHealthRecord(request, email);
        Map<String, String> response = new HashMap<>();
        response.put("name", result.getName());
        response.put("message", result.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<List<HealthMetricResponseDTO>> getHealthRecord() {
        String email = util.getEmail();
        List<HealthMetricResponseDTO> result = healthMetricService.getHealthRecord(email);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<MemberDashboardResponse> getDashboard() {
        String email = util.getEmail();
        MemberDashboardResponse result = healthMetricService.getDashboard(email);
        return ResponseEntity.ok(result);
    }

}
