package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedClient;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;

public record CreatedClientImpl(Long userId, Long roleId) implements ICreatedClient {
  @Override
  public Long getClientId() {
    return userId;
  }

  @Override
  public Long getRoleId() {
    return roleId;
  }
}
