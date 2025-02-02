package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.factory;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailInput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailOutput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.impl.AdminDisplayProfessionalDetailInputImpl;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.impl.AdminDisplayProfessionalDetailOutputImpl;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.useCase.AdminDisplayProfessionalDetailUseCase;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivateListOutput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.impl.ProfessionalToActivateListImpl;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.useCase.AdminDisplayProfessionalToActivateListUseCase;

import java.util.List;

public class Factory {
  public static IAdminDisplayProfessionalDetailInput getAdminDisplayProfessionalDetailInputImpl(String email) {
    return new AdminDisplayProfessionalDetailInputImpl(email);
  }

  public static AdminDisplayProfessionalDetailUseCase.Output getOutput(IAdminDisplayProfessionalDetailOutput adminDisplayProfessionalDetailOutput) {
    return new AdminDisplayProfessionalDetailUseCase.Output(adminDisplayProfessionalDetailOutput);
  }

  public static IAdminDisplayProfessionalDetailOutput getAdminDisplayProfessionalDetailOutputImpl(IProfessionalDetail professionalDetail) {
    return new AdminDisplayProfessionalDetailOutputImpl(professionalDetail);
  }
}
