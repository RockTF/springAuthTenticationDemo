package com.altimetrik.ee.demo.security.exceptionhandrel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    LocalDateTime currentTimeStamp = LocalDateTime.now();
    String message =
        (accessDeniedException != null && accessDeniedException.getMessage() != null)
            ? accessDeniedException.getMessage()
            : "Authorization failed";
    String path = request.getRequestURI();
    response.setHeader("Access-Control-Allow-Origin", "Authorization failed");
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json;charset=UTF-8");
    String jsonResponse =
        String.format(
            "{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
            currentTimeStamp,
            HttpStatus.FORBIDDEN.value(),
            HttpStatus.FORBIDDEN.getReasonPhrase(),
            message,
            path);
    response.getWriter().write(jsonResponse);
  }
}
