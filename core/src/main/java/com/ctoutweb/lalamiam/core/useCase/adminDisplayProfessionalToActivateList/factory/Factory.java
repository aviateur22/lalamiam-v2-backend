package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.factory;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivateListOutput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.impl.ProfessionalToActivateListImpl;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.useCase.AdminDisplayProfessionalToActivateListUseCase;

import java.util.List;

public class Factory {
  public static IProfessionalToActivateListOutput getProfessionalToActivateListOutputImpl(List<IProfessionalToActivate> professionalListToActivate) {
    return new ProfessionalToActivateListImpl(professionalListToActivate);
  }
  public static AdminDisplayProfessionalToActivateListUseCase.Output getUseCaseOutput(IProfessionalToActivateListOutput boundaryOutput) {
    return new AdminDisplayProfessionalToActivateListUseCase.Output(boundaryOutput);
  }

}
