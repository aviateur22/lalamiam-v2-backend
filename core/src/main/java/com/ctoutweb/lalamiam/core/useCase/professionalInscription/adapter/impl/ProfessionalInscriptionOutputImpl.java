package com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessioanlInscriptionOutput;

public record ProfessionalInscriptionOutputImpl(Long professionalId, String responseMessage) implements IProfessioanlInscriptionOutput {
  @Override
  public String getResponseMessage() {
    return responseMessage;
  }

  @Override
  public Long getProfessionalId() {
    return professionalId;
  }
}
