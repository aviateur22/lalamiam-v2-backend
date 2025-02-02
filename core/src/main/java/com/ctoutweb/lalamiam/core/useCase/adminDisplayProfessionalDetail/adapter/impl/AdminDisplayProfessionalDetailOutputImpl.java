package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.impl;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailOutput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;

public record AdminDisplayProfessionalDetailOutputImpl(IProfessionalDetail professionalDetail) implements IAdminDisplayProfessionalDetailOutput {
  @Override
  public IProfessionalDetail getProfessionalDetail() {
    return professionalDetail;
  }
}
