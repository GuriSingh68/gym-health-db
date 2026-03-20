package egen5208.gym.service;

import org.springframework.stereotype.Service;

import egen5208.gym.dto.FitnessGoal.FitnessGoalResponseDTO;
import egen5208.gym.dto.FitnessGoal.GoalRequestDTO;
import egen5208.gym.model.FitnessGoals;
import egen5208.gym.model.User;
import egen5208.gym.model.enums.GoalStatus;
import egen5208.gym.repo.FitnessGoalsRepo;
import egen5208.gym.repo.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FitnessService {

    private final FitnessGoalsRepo fitnessGoalsRepo;
    private final UserRepo userRepo;

    public FitnessGoalResponseDTO createGoals(GoalRequestDTO goalRequestDTO) {
        User user = userRepo.findById(goalRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        FitnessGoals goals = new FitnessGoals();
        goals.setMember(user);
        goals.setGoalDescription(goalRequestDTO.getGoalDescription());
        goals.setStatus(GoalStatus.valueOf(goalRequestDTO.getStatus().toUpperCase()));
        fitnessGoalsRepo.save(goals);
        FitnessGoalResponseDTO responseDTO = new FitnessGoalResponseDTO();
        responseDTO.setGoalId(goals.getGoalId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setName(user.getFirstName() + " " + user.getLastName());
        responseDTO.setGoalDescription(goals.getGoalDescription());
        responseDTO.setStatus(goals.getStatus().name());
        return responseDTO;
    }
}
