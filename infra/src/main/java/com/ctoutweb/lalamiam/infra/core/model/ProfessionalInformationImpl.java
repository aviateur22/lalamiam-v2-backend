package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalInformation;

public record ProfessionalInformationImpl(Long professionalId, String email, IProfessionalAccountInformation professionalAccountInformation) implements IProfessionalInformation {
  @Override
  public Long getProfessionalId() {
    return professionalId;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public IProfessionalAccountInformation getProfessionalAccountInformation() {
    return professionalAccountInformation;
  }
}
