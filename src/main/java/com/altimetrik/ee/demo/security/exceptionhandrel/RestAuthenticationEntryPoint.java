package com.altimetrik.ee.demo.security.exceptionhandrel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component("restAuthenticationEntryPoint")
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request,
      jakarta.servlet.http.HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, jakarta.servlet.ServletException {

    final String expired = (String) request.getAttribute("expired");
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    String message = "";
    if (expired != null) {
      message = " Token Expired";
    } else {
      message = authException.getMessage();
    }
    response.getOutputStream().println("{ \"error\": \"" + message + "\" }");
  }
}
