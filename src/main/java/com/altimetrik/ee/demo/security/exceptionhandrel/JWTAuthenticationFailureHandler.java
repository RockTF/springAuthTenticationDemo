package com.altimetrik.ee.demo.security.exceptionhandrel;

import java.io.IOException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      jakarta.servlet.http.HttpServletRequest request,
      jakarta.servlet.http.HttpServletResponse response,
      AuthenticationException exception)
      throws IOException, jakarta.servlet.ServletException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response.getWriter().append(json());
  }

  private String json() {
    long date = new Date().getTime();
    return "{\"timestamp\": "
        + date
        + ", "
        + "\"status\": 401, "
        + "\"error\": \"Unauthorized\", "
        + "\"message\": \"Authentication failed: bad credentials\", "
        + "\"path\": \"/login\"}";
  }
}
