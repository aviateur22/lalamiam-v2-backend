package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessional;

public record CreateProfessionalImpl(Long professionalId) implements ICreatedProfessional {
  @Override
  public Long getProfessionalId() {
    return professionalId;
  }
}
