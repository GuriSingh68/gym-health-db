package egen5208.gym.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "trainers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100)
    private String specialty;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainerAvailability> availabilities;
}