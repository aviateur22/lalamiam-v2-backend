package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessionalInscriptionConfirmationInput;

public record ProfessionalInscriptionConfirmationInputBoundaryImpl(String email)
        implements IProfessionalInscriptionConfirmationInput {

  @Override
  public String getProfessionalEmail() {
    return email;
  }
}
