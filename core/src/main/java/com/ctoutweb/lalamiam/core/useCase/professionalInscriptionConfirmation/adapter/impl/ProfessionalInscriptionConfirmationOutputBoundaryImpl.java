package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessioanlInscriptionConfirmationOutput;

public record ProfessionalInscriptionConfirmationOutputBoundaryImpl(String responseMessage) implements IProfessioanlInscriptionConfirmationOutput {
  @Override
  public String getResponseMessage() {
    return responseMessage;
  }
}
