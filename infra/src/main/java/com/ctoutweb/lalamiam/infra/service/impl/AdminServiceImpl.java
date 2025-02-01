package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.useCase.impl.AdminDisplayProfessionalInscriptionDocumentUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.useCase.AdminDisplayProfessionalToActivateListUseCase;
import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateResumeDto;
import com.ctoutweb.lalamiam.infra.mapper.ResumeProfessionalToActivateMapper;
import com.ctoutweb.lalamiam.infra.service.IAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {
  private final AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase;
  private final AdminDisplayProfessionalToActivateListUseCase adminDisplayProfessionalToActivateListUseCase;

  private final ResumeProfessionalToActivateMapper resumeProfessionalToActivateMapper;

  public AdminServiceImpl(
          AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase,
          AdminDisplayProfessionalToActivateListUseCase adminDisplayProfessionalToActivateListUseCase,
          ResumeProfessionalToActivateMapper resumeProfessionalToActivateMapper) {
    this.adminDisplayProfessionalInscriptionDocumentUseCase = adminDisplayProfessionalInscriptionDocumentUseCase;
    this.adminDisplayProfessionalToActivateListUseCase = adminDisplayProfessionalToActivateListUseCase;
    this.resumeProfessionalToActivateMapper = resumeProfessionalToActivateMapper;
  }

  @Override
  public List<ProfessionalToActivateResumeDto> findProfessionalsToActivates() {
    AdminDisplayProfessionalToActivateListUseCase.Input input = AdminDisplayProfessionalToActivateListUseCase.Input.getInput();
    AdminDisplayProfessionalToActivateListUseCase.Output output = adminDisplayProfessionalToActivateListUseCase.execute(input);

   return output
            .getOutputBoundary()
            .getProfessionalToActivateList()
            .stream()
            .map(resumeProfessionalToActivateMapper::map)
            .toList();
  }
}
