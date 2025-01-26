package com.ctoutweb.lalamiam.infra.model.core.adapter;

import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalInformation;

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
