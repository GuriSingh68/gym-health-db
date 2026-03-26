package egen5208.gym.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import egen5208.gym.dto.FitnessGoal.FitnessGoalResponseDTO;
import egen5208.gym.dto.FitnessGoal.GoalRequestDTO;
import egen5208.gym.dto.FitnessGoal.GoalUpdateDTO;
import egen5208.gym.exception.GoalNotFoundException;
import egen5208.gym.exception.UserNotfoundException;
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

    public FitnessGoalResponseDTO createGoals(GoalRequestDTO goalRequestDTO, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotfoundException("User not found"));
        FitnessGoals goals = new FitnessGoals();
        goals.setMember(user);
        goals.setGoalDescription(goalRequestDTO.getGoalDescription());
        goals.setStatus(GoalStatus.valueOf(goalRequestDTO.getStatus().toUpperCase()));
        goals.setCreatedAt(LocalDateTime.now());
        fitnessGoalsRepo.save(goals);
        FitnessGoalResponseDTO responseDTO = new FitnessGoalResponseDTO();
        responseDTO.setGoalId(goals.getGoalId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setName(user.getFirstName() + " " + user.getLastName());
        responseDTO.setGoalDescription(goals.getGoalDescription());
        responseDTO.setStatus(goals.getStatus().name());
        return responseDTO;
    }

    public FitnessGoalResponseDTO updateGoals(Long id, String email, GoalUpdateDTO goalUpdateDto) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotfoundException("User not found"));
        FitnessGoals goals = fitnessGoalsRepo.findById(id)
                .orElseThrow(() -> new GoalNotFoundException("Goal not found"));
        if (!goals.getMember().getEmail().equals(email)) {
            throw new UserNotfoundException("You are not authorized to update this goal");
        }
        if (goalUpdateDto.getGoalDescription() != null) {
            goals.setGoalDescription(goalUpdateDto.getGoalDescription());
        }
        if (goalUpdateDto.getStatus() != null) {
            goals.setStatus(GoalStatus.valueOf(goalUpdateDto.getStatus().toUpperCase()));
        }
        fitnessGoalsRepo.save(goals);
        FitnessGoalResponseDTO responseDTO = new FitnessGoalResponseDTO();
        responseDTO.setGoalId(goals.getGoalId());
        responseDTO.setEmail(goals.getMember().getEmail());
        responseDTO.setName(goals.getMember().getFirstName() + " " + goals.getMember().getLastName());
        responseDTO.setGoalDescription(goals.getGoalDescription());
        responseDTO.setStatus(goals.getStatus().name());
        return responseDTO;
    }
}
