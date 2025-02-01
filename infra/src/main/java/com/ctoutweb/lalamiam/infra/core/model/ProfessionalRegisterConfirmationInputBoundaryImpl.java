package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessionalInscriptionConfirmationInput;

public record ProfessionalRegisterConfirmationInputBoundaryImpl(String email)
        implements IProfessionalInscriptionConfirmationInput {
  @Override
  public String getProfessionalEmail() {
    return email;
  }
}
