package com.ctoutweb.lalamiam.infra.security.authentication;

import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final IMessageService messageService;

  public CustomAuthenticationEntryPoint(IMessageService messageService) {
    this.messageService = messageService;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error", messageService.getMessage("jwt.unvalid"));
    ObjectMapper mapper = new ObjectMapper();
    response.getOutputStream().write(mapper.writeValueAsBytes(errorResponse));
  }
}
