package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessionalInscriptionConfirmationInput;

public record ProfessionalInscriptionConfirmationInputBoundaryImpl(String email)
        implements IProfessionalInscriptionConfirmationInput {

  @Override
  public String getProfessionalEmail() {
    return email;
  }
}
