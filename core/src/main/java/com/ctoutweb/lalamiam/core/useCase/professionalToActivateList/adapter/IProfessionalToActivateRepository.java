package com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter;

import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.entity.IProfessionalToActivate;

import java.util.List;

public interface IProfessionalToActivateRepository {
  public List<IProfessionalToActivate> getProfessionalToActivateList();
}
