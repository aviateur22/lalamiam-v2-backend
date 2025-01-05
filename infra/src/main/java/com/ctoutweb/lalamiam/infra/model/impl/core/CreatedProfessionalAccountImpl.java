package com.ctoutweb.lalamiam.infra.model.impl.core;

import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription.ICreatedProfessionalAccount;

public record CreatedProfessionalAccountImpl(Long accountId) implements ICreatedProfessionalAccount {
  @Override
  public Long getAccountId() {
    return accountId;
  }
}
