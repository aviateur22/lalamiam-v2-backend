package com.ctoutweb.lalamiam.infra.model.core.adapter;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessionalInscriptionConfirmationInput;

public record ProfessionalRegisterConfirmationInputBoundaryImpl(String email)
        implements IProfessionalInscriptionConfirmationInput {
  @Override
  public String getProfessionalEmail() {
    return email;
  }
}
