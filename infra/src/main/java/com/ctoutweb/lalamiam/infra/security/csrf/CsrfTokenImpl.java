package com.ctoutweb.lalamiam.infra.security.csrf;

import org.springframework.security.web.csrf.CsrfToken;

public record CsrfTokenImpl(String headerName, String parameterName, String token) implements CsrfToken {
  @Override
  public String getHeaderName() {
    return headerName;
  }

  @Override
  public String getParameterName() {
    return parameterName;
  }

  @Override
  public String getToken() {
    return token;
  }
}
