package ru.fridrock.jir_backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProjectEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID projectId;
  @ManyToOne
  private UserEntity owner;
  private String name;
  @OneToMany(mappedBy = "project")
  private Set<TaskEntity> tasks;
}
