package ru.fridrock.jir_backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false)
  private String email;
  @Column(nullable = false)
  private String username;
  @Column(name = "password_hash",  nullable = false)
  private String passwordHash;

  @OneToMany(mappedBy = "owner")
  private Set<ProjectEntity> projects;
}