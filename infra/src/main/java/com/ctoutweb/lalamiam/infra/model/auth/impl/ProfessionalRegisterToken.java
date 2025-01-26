package com.ctoutweb.lalamiam.infra.model.auth.impl;

import com.ctoutweb.lalamiam.infra.model.auth.IProfessionalRegisterToken;

public record ProfessionalRegisterToken(String encryptUrlToken, String plainTextEmailToken) implements IProfessionalRegisterToken {
  @Override
  public String getPlainTextEmailToken() {
    return plainTextEmailToken;
  }

  @Override
  public String getEncryptUrlToken() {
    return encryptUrlToken;
  }
}
