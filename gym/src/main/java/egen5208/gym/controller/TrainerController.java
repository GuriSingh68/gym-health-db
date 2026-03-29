package egen5208.gym.controller;

import egen5208.gym.dto.Trainer.AvailabilityPatchRequestDTO;
import egen5208.gym.dto.Trainer.TrainerAvailabilityRequestDTO;
import egen5208.gym.dto.Trainer.TrainerRespnse;
import egen5208.gym.service.TrainerService;
import egen5208.gym.utils.util;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/trainers")
@AllArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;

    @PostMapping("/availability")
    @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
    public ResponseEntity<TrainerRespnse> addAvailability(
            @Valid @RequestBody TrainerAvailabilityRequestDTO availability) {
        String email = util.getEmail();

        TrainerRespnse trainerRespnse = trainerService.addAvailability(email, availability);
        return ResponseEntity.ok(trainerRespnse);
    }

    @PatchMapping("/availability/{availabilityId}")
    @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
    public ResponseEntity<TrainerRespnse> updateAvailability(
            @PathVariable Long availabilityId, @Valid @RequestBody AvailabilityPatchRequestDTO availability) {
        TrainerRespnse trainerRespnse = trainerService.updateAvailability(availabilityId, availability);
        return ResponseEntity.ok(trainerRespnse);
    }

    @DeleteMapping("/availability/{availabilityId}")
    @PreAuthorize("hasAnyRole('TRAINER', 'ADMIN')")
    public ResponseEntity<Map<String, String>> deleteAvailability(
            @PathVariable Long availabilityId) {
        trainerService.deleteAvailability(availabilityId);
        return ResponseEntity.ok(Map.of("Trainer", util.getEmail(), "message", "Availability deleted successfully"));
    }
}
