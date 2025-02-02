package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.impl;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailInput;

public record AdminDisplayProfessionalDetailInputImpl(String email) implements IAdminDisplayProfessionalDetailInput {
  @Override
  public String getEmail() {
    return email;
  }
}
