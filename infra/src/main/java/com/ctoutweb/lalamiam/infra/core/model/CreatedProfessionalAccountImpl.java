package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessionalAccount;

public record CreatedProfessionalAccountImpl(Long accountId) implements ICreatedProfessionalAccount {
  @Override
  public Long getAccountId() {
    return accountId;
  }
}
