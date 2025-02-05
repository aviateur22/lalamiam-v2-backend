package com.ctoutweb.lalamiam.core.useCase.clientInscription.port.impl;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionOutput;

public record ClientInscriptionOutputImpl(Long clientId, Long accountId) implements IClientInscriptionOutput {
  @Override
  public Long getUserId() {
    return clientId;
  }

  @Override
  public Long getUserAccountId() {
    return accountId;
  }
}
