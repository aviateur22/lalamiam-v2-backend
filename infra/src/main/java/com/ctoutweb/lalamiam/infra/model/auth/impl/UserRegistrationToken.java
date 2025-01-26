package com.ctoutweb.lalamiam.infra.model.auth.impl;

import com.ctoutweb.lalamiam.infra.model.auth.IUserRegisterToken;

public record UserRegistrationToken(String encryptUrlToken) implements IUserRegisterToken {
  @Override
  public String getEncryptUrlToken() {
    return encryptUrlToken;
  }
}
