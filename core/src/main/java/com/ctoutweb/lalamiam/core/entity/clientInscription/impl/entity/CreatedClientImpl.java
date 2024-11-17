package com.ctoutweb.lalamiam.core.entity.clientInscription.impl.entity;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedClient;

public record CreatedClientImpl(Long clientId) implements ICreatedClient {
  @Override
  public Long getClientId() {
    return clientId;
  }
}
