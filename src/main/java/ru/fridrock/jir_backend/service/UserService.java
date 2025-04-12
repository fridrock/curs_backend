package ru.fridrock.jir_backend.service;

import org.springframework.stereotype.Service;
import ru.fridrock.jir_backend.dto.input.AuthRequest;
import ru.fridrock.jir_backend.dto.input.RegisterRequest;
import ru.fridrock.jir_backend.utils.jwt.UserToken;

import java.util.UUID;

@Service
public class UserService {
  //TODO realization of creation user
  public UserToken createUser(RegisterRequest dto){
    return new UserToken(dto.username(), UUID.randomUUID());
  }
  public UserToken authenticateUser(AuthRequest dto){
    return new UserToken(dto.username(), UUID.randomUUID());
  }
}
