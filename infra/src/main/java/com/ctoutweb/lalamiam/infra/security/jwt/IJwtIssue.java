package com.ctoutweb.lalamiam.infra.security.jwt;

import java.time.ZonedDateTime;

public interface IJwtIssue {
  String getJwtId();
  String getJwtToken();
  ZonedDateTime getExpiredAt();
}
