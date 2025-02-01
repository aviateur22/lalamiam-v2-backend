package com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.factory;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter.IProfessionalToActivateListOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter.impl.ProfessionalToActivateListImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.entity.IProfessionalToActivate;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.useCase.AdminDisplayProfessionalToActivateListUseCase;

import java.util.List;

public class Factory {
  public static IProfessionalToActivateListOutput getProfessionalToActivateListOutputImpl(List<IProfessionalToActivate> professionalListToActivate) {
    return new ProfessionalToActivateListImpl(professionalListToActivate);
  }
  public static AdminDisplayProfessionalToActivateListUseCase.Output getUseCaseOutput(IProfessionalToActivateListOutput boundaryOutput) {
    return new AdminDisplayProfessionalToActivateListUseCase.Output(boundaryOutput);
  }

}
