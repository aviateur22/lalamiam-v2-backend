package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;

public record CreatedClientImpl(UserEntity user) implements IClientInscription.ICreatedClient {
  @Override
  public Long getClientId() {
    return user.getId();
  }
}
