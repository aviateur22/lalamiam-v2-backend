package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.infra.model.IUserLoginStatus;

import java.time.ZonedDateTime;

public record UserLoginStatusImpl(boolean isLoginAuthorize, ZonedDateTime recoveryLoginTime) implements IUserLoginStatus {
  @Override
  public boolean isLoginAuthorize() {
    return isLoginAuthorize;
  }

  @Override
  public ZonedDateTime getRecoveryLoginTime() {
    return recoveryLoginTime;
  }
}
