package entity.clientInscription.impl.entity;

import entity.clientInscription.IClientInscription.ICreatedClient;

public record CreatedClientImpl(Long clientId) implements ICreatedClient {
  @Override
  public Long getClientId() {
    return clientId;
  }
}
