package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ISavedInscriptionDocuments;

import java.util.List;
public record SaveDocumentImpl(List<Long> documents) implements ISavedInscriptionDocuments {
  @Override
  public List<Long> getInscriptionDocumentId() {
    return documents;
  }
}
