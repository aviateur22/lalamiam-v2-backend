package com.ctoutweb.lalamiam.infra.security.authentication;

import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final IMessageService messageService;

  public CustomAccessDeniedHandler(IMessageService messageService) {
    this.messageService = messageService;
  }
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("error",messageService.getMessage("forbidden_access"));
    ObjectMapper mapper = new ObjectMapper();
    response.getOutputStream().write(mapper.writeValueAsBytes(errorResponse));
  }
}
