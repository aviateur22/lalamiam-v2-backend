package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.impl;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivateListOutput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;

import java.util.List;

public record ProfessionalToActivateListImpl(List<IProfessionalToActivate> professionalListToActivate) implements IProfessionalToActivateListOutput {
  @Override
  public List<IProfessionalToActivate> getProfessionalToActivateList() {
    return professionalListToActivate;
  }
}
