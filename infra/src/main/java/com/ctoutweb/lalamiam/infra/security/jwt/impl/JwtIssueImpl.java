package com.ctoutweb.lalamiam.infra.security.jwt.impl;

import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;

import java.time.ZonedDateTime;

public record JwtIssueImpl(String jwtId, String jwtToken, ZonedDateTime expiredAt) implements IJwtIssue {
  @Override
  public String getJwtId() {
    return jwtId;
  }

  @Override
  public String getJwtToken() {
    return jwtToken;
  }

  @Override
  public ZonedDateTime getExpiredAt() {
    return expiredAt;
  }
}
