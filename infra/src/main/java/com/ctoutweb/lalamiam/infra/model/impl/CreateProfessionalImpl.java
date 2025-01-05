package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription;

public record CreateProfessionalImpl(Long professionalId) implements IProfessionalInscription.ICreatedProfessional {
  @Override
  public Long getProfessionalId() {
    return professionalId;
  }
}
