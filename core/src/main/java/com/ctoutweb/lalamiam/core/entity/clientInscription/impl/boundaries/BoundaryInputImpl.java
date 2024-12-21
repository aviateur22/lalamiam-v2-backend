package com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;

public record BoundaryInputImpl(
        String hashPassword,
        String email,
        String name
) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(
          String hashPassword,
          String email,
          String name
  ) {
    return new BoundaryInputImpl(
            hashPassword,
            email,
            name
    );
  }

  @Override
  public String getHashPassword() {
    return hashPassword;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getUserName() {
    return name;
  }
}
