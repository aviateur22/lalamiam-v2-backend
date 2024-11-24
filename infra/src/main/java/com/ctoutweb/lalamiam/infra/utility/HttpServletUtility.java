package com.ctoutweb.lalamiam.infra.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpServletUtility {
  /**
   *
   * @param response
   * @param messageKey
   * @param message
   */
  public static HttpServletResponse formatResponseMessage(HttpServletResponse response, String messageKey, String message)
          throws IOException {
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put(messageKey, message);
    ObjectMapper mapper = new ObjectMapper();
    response.getOutputStream().write(mapper.writeValueAsBytes(errorResponse));
    return response;
  }
}
