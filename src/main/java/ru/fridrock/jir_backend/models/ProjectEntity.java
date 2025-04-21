package ru.fridrock.jir_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
