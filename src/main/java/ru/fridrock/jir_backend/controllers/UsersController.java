package ru.fridrock.jir_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fridrock.jir_backend.dto.input.RegisterRequest;
import ru.fridrock.jir_backend.dto.output.AuthResponse;

@RestController
@RequestMapping("/api/users")
public class UsersController {
  @PostMapping("/reg")
  public AuthResponse register(RegisterRequest dto){
    return new AuthResponse("bullshittoken");
  }
  @GetMapping("/get")
  public String hello(){
    return "hello";
  }
}
