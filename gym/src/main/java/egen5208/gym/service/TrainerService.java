package egen5208.gym.service;

import org.springframework.stereotype.Service;

import egen5208.gym.dto.Trainer.AvailabilityPatchRequestDTO;
import egen5208.gym.dto.Trainer.TrainerAvailabilityRequestDTO;
import egen5208.gym.dto.Trainer.TrainerRespnse;
import egen5208.gym.exception.TimeException;
import egen5208.gym.exception.UserNotfoundException;
import egen5208.gym.model.Trainer;
import egen5208.gym.model.TrainerAvailability;
import egen5208.gym.model.User;
import egen5208.gym.model.enums.UserRole;
import egen5208.gym.repo.TrainerAvailabilityRepo;
import egen5208.gym.repo.TrainerRepo;
import egen5208.gym.repo.UserRepo;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class TrainerService {
        private final TrainerRepo trainerRepo;
        private final UserRepo userRepo;
        private final TrainerAvailabilityRepo trainerAvailabilityRepo;

        public TrainerRespnse addAvailability(String email, TrainerAvailabilityRequestDTO availability) {
                User user = userRepo.findByEmail(email)
                                .orElseThrow(() -> new UserNotfoundException("User not found"));

                Trainer trainer;
                if (user.getRole() == UserRole.ADMIN
                                || user.getRole() == UserRole.TRAINER && availability.getTrainerId() != null) {
                        trainer = trainerRepo.findById(availability.getTrainerId())
                                        .orElseThrow(() -> new UserNotfoundException(
                                                        "Trainer not found for ID: " + availability.getTrainerId()));
                } else {
                        trainer = trainerRepo.findByUser(user)
                                        .orElseThrow(() -> new UserNotfoundException(
                                                        "Trainer record not found for user: " + email));
                }
                LocalTime startTime = LocalTime.parse(availability.getStartTime());
                LocalTime endTime = LocalTime.parse(availability.getEndTime());
                if (endTime.isBefore(startTime)) {
                        throw new TimeException("Start time must be before end time");
                }
                TrainerAvailability trainerAvailability = new TrainerAvailability();
                trainerAvailability.setTrainer(trainer);
                trainerAvailability.setAvailableDate(LocalDate.parse(availability.getAvailableDate()));
                trainerAvailability.setStartTime(startTime);
                trainerAvailability.setEndTime(endTime);
                trainerAvailability.setAvailable(true);

                TrainerAvailability saved = trainerAvailabilityRepo.save(trainerAvailability);

                return new TrainerRespnse(
                                saved.getAvailabilityId(),
                                trainer.getTrainerId(),
                                user.getFirstName() + " " + user.getLastName(),
                                saved.getAvailableDate(),
                                saved.getStartTime(),
                                saved.getEndTime());
        }

        public TrainerRespnse updateAvailability(Long availabilityId, AvailabilityPatchRequestDTO availability) {
                TrainerAvailability trainerAvailability = trainerAvailabilityRepo.findById(availabilityId)
                                .orElseThrow(() -> new UserNotfoundException("Availability not found"));

                if (availability.getAvailableDate() != null) {
                        trainerAvailability.setAvailableDate(LocalDate.parse(availability.getAvailableDate()));
                }

                LocalTime startTime = availability.getStartTime() != null
                                ? LocalTime.parse(availability.getStartTime())
                                : null;
                LocalTime endTime = availability.getEndTime() != null
                                ? LocalTime.parse(availability.getEndTime())
                                : null;
                if (startTime != null && endTime != null && endTime.isBefore(startTime)) {
                        throw new TimeException("Start time must be before end time");
                }

                if (startTime != null) {
                        trainerAvailability.setStartTime(startTime);
                }
                if (endTime != null) {
                        trainerAvailability.setEndTime(endTime);
                }
                if (availability.getIsAvailable() != null) {
                        trainerAvailability.setAvailable(availability.getIsAvailable());
                }

                TrainerAvailability saved = trainerAvailabilityRepo.save(trainerAvailability);
                return new TrainerRespnse(
                                saved.getAvailabilityId(),
                                saved.getTrainer().getTrainerId(),
                                saved.getTrainer().getUser().getFirstName() + " "
                                                + saved.getTrainer().getUser().getLastName(),
                                saved.getAvailableDate(),
                                saved.getStartTime(),
                                saved.getEndTime());
        }

        public TrainerRespnse deleteAvailability(Long availabilityId) {
                TrainerAvailability trainerAvailability = trainerAvailabilityRepo.findById(availabilityId)
                                .orElseThrow(() -> new UserNotfoundException("Availability not found"));
                trainerAvailabilityRepo.delete(trainerAvailability);
                return new TrainerRespnse(
                                trainerAvailability.getAvailabilityId(),
                                trainerAvailability.getTrainer().getTrainerId(),
                                trainerAvailability.getTrainer().getUser().getFirstName() + " "
                                                + trainerAvailability.getTrainer().getUser().getLastName(),
                                trainerAvailability.getAvailableDate(),
                                trainerAvailability.getStartTime(),
                                trainerAvailability.getEndTime());
        }

}
