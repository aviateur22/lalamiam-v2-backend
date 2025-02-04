package com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessioanlInscriptionOutput;

import java.util.List;

public record ProfessionalInscriptionOutputImpl(
        Long professionalId,
        Long accountId,
        List<Long> documentIds,
        String responseMessage) implements IProfessioanlInscriptionOutput {
  @Override
  public String getResponseMessage() {
    return responseMessage;
  }

  @Override
  public Long getProfessionalId() {
    return professionalId;
  }

  @Override
  public Long getProfessionalAccountId() {
    return accountId;
  }

  @Override
  public List<Long> getInscriptionDocumentsIds() {
    return documentIds;
  }
}
