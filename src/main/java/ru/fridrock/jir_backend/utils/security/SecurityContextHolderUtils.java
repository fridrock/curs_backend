package ru.fridrock.jir_backend.utils.security;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.fridrock.jir_backend.security.JirAuthentication;

public class SecurityContextHolderUtils {
  public static JirAuthentication getUser(){
    return (JirAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }
}
