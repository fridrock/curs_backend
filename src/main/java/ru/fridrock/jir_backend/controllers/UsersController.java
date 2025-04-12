package ru.fridrock.jir_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fridrock.jir_backend.dto.input.AuthRequest;
import ru.fridrock.jir_backend.dto.input.RegisterRequest;
import ru.fridrock.jir_backend.dto.output.AuthResponse;
import ru.fridrock.jir_backend.security.JirUserDetails;
import ru.fridrock.jir_backend.service.UserService;
import ru.fridrock.jir_backend.utils.jwt.JwtTokenUtils;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {
  private final JwtTokenUtils jwtTokenUtils;
  private final UserDetailsService userDetailsService;
  private final UserService userService;

  @PostMapping("/reg")
  public AuthResponse register(@RequestBody RegisterRequest dto) {
    //TODO create user with such credentials
    return new AuthResponse(jwtTokenUtils.generateToken(new JirUserDetails("Somename")));

  }

  @PostMapping("/auth")
  public AuthResponse auth(@RequestBody AuthRequest dto) {
    //TODO check username && password
    return new AuthResponse(jwtTokenUtils.generateToken(userDetailsService.loadUserByUsername(dto.username())));
  }
}
