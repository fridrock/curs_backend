package ru.fridrock.jir_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fridrock.jir_backend.dto.input.AuthRequest;
import ru.fridrock.jir_backend.dto.input.RegisterRequest;
import ru.fridrock.jir_backend.exception.UnauthorizedException;
import ru.fridrock.jir_backend.models.UserEntity;
import ru.fridrock.jir_backend.repository.UserRepository;
import ru.fridrock.jir_backend.utils.jwt.UserToken;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserToken createUser(RegisterRequest dto) {
    var userEntity = UserEntity.builder()
        .username(dto.username())
        .email(dto.email())
        .passwordHash(passwordEncoder.encode(dto.password()))
        .build();

    userEntity = userRepository.save(userEntity);
    return new UserToken(userEntity.getUsername(), userEntity.getId());
  }

  public UserToken authenticateUser(AuthRequest dto) {
    var userEntity = userRepository.findByUsername(dto.username()).orElseThrow(
        () -> new UnauthorizedException("no user with username: " + dto.username())
    );
    if(!passwordEncoder.matches(dto.password(), userEntity.getPasswordHash())){
      throw new UnauthorizedException("wrong password");
    }
    return new UserToken(dto.username(), UUID.randomUUID());
  }
}
