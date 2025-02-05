package com.ctoutweb.lalamiam.core.useCase.clientInscription.port.impl;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;

public record ClientInscriptionInputImpl(String hashPassword, String email, String nickName) implements IClientInscriptionInput {
  @Override
  public String getHashPassword() {
    return hashPassword;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getNickName() {
    return nickName;
  }
}
