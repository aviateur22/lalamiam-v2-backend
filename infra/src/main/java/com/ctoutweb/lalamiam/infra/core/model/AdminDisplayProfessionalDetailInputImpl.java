package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailInput;

public record AdminDisplayProfessionalDetailInputImpl(String email) implements IAdminDisplayProfessionalDetailInput {
  @Override
  public String getEmail() {
    return email;
  }
}
