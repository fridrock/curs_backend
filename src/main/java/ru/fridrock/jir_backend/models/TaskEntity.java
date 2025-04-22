package ru.fridrock.jir_backend.models;

import jakarta.persistence.*;
import lombok.*;
import ru.fridrock.jir_backend.models.enums.TaskPriority;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID taskId;
    private String title;
    private String description;
    @ManyToOne
    private ProjectEntity project;
    private LocalDateTime issued;
    private LocalDateTime deadline;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private Integer hoursSpent;
}
