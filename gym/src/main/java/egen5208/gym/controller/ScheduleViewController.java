package egen5208.gym.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import egen5208.gym.model.GroupClassSessionView;
import egen5208.gym.model.PersonalSessionView;
import egen5208.gym.service.ScheduleViewService;
import egen5208.gym.utils.util;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
public class ScheduleViewController {
    private final ScheduleViewService scheduleViewService;

    @GetMapping("/personalSession")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN','MEMBER')")
    public ResponseEntity<List<PersonalSessionView>> getAllSchedule() {
        String email = util.getEmail();
        return ResponseEntity.ok(scheduleViewService.getPersonalSchedule(email));
    }

    @GetMapping("/groupSession")
    @PreAuthorize("hasAnyRole('TRAINER','ADMIN','MEMBER')")
    public ResponseEntity<List<GroupClassSessionView>> getAllGroupSchedule() {
        String email = util.getEmail();
        return ResponseEntity.ok(scheduleViewService.getGroupSchedule(email));
    }
}
