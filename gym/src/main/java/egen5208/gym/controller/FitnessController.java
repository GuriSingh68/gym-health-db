package egen5208.gym.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egen5208.gym.dto.FitnessGoal.FitnessGoalResponseDTO;
import egen5208.gym.dto.FitnessGoal.GoalRequestDTO;
import egen5208.gym.dto.FitnessGoal.GoalUpdateDTO;
import egen5208.gym.service.FitnessService;
import egen5208.gym.utils.util;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class FitnessController {
    private final FitnessService fitnessService;

    @PostMapping("")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<Map<String, String>> createGoals(@RequestBody GoalRequestDTO goalRequestDTO) {
        String email = util.getEmail();
        FitnessGoalResponseDTO result = fitnessService.createGoals(goalRequestDTO, email);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Goal created successfully");
        response.put("user", result.getName());
        response.put("goal", result.getGoalDescription());
        response.put("status", result.getStatus());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
    public ResponseEntity<Map<String, String>> createGoals(@PathVariable Long id,
            @RequestBody GoalUpdateDTO goalUpdateDto) {
        String email = util.getEmail();
        FitnessGoalResponseDTO result = fitnessService.updateGoals(id, email, goalUpdateDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Goal updated successfully");
        response.put("user", result.getName());
        response.put("goal", result.getGoalDescription());
        response.put("status", result.getStatus());

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
