package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessioanlInscriptionConfirmationOutput;

public record ProfessionalInscriptionConfirmationOutputBoundaryImpl(String responseMessage) implements IProfessioanlInscriptionConfirmationOutput {
  @Override
  public String getResponseMessage() {
    return responseMessage;
  }
}
