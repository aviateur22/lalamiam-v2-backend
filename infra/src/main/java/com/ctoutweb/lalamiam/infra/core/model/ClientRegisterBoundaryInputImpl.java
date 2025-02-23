package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;

public record ClientRegisterBoundaryInputImpl(
        String hashPassword,
        String email,
        String userName

) implements IClientInscriptionInput {
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
    return userName;
  }
}
