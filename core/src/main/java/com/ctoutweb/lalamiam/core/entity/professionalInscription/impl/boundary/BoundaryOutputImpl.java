package com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary;

import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryOutputAdapter;

public record BoundaryOutputImpl(Long professionalId, String reponseMessage) implements IBoundaryOutputAdapter {
  public static BoundaryOutputImpl getBoundaryInputImpl(String successMessage, long professionalId) {
    return new BoundaryOutputImpl(professionalId, successMessage);
  }

  @Override
  public Long getProfessionalId() {
    return professionalId;
  }

  @Override
  public String getResponseMessage() {
    return reponseMessage;
  }
}
