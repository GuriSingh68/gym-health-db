package egen5208.gym.service;

import java.util.List;

import org.springframework.stereotype.Service;

import egen5208.gym.model.GroupClassSessionView;
import egen5208.gym.model.PersonalSessionView;
import egen5208.gym.model.User;
import egen5208.gym.repo.GroupClassSessionRepository;
import egen5208.gym.repo.PersonalSessionRepository;
import egen5208.gym.repo.UserRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleViewService {
    private final UserRepo userRepo;
    private final PersonalSessionRepository personalSessionRepository;
    private final GroupClassSessionRepository groupClassSessionRepository;

    public List<PersonalSessionView> getPersonalSchedule(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Long memberId = user.getUserId();
        return personalSessionRepository.getPersonalSessions(memberId);
    }

    public List<GroupClassSessionView> getGroupSchedule(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Long memberId = user.getUserId();
        return groupClassSessionRepository.getGroupSessions(memberId);
    }
}