package com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter.IProfessionalToActivateListOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.entity.IProfessionalToActivate;

import java.util.List;

public record ProfessionalToActivateListImpl(List<IProfessionalToActivate> professionalListToActivate) implements IProfessionalToActivateListOutput {
  @Override
  public List<IProfessionalToActivate> getProfessionalToActivateList() {
    return professionalListToActivate;
  }
}
