package ru.fridrock.jir_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<CustomProblemDetails> handleUnauthorizedException(UnauthorizedException ex){
    int status = HttpStatus.UNAUTHORIZED.value();
    CustomProblemDetails customProblemDetails = CustomProblemDetails.builder()
        .status(status)
        .title("Error authorizing")
        .details(ex.getMessage())
        .build();
    return ResponseEntity.status(status).body(customProblemDetails);
  }
}
