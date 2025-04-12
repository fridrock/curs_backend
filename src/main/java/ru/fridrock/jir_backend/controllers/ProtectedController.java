package ru.fridrock.jir_backend.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/protected")
@SecurityRequirement(name="Authorization")
public class ProtectedController {
  @GetMapping("/")
  public String hello(){
    return "hello";
  }
}
