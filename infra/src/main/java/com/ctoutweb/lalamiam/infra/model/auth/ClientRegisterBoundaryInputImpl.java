package com.ctoutweb.lalamiam.infra.model.auth;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;

public record ClientRegisterBoundaryInputImpl(
        String hashPassword,
        String email,
        String userName

) implements IBoundaryInputAdapter {
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
