package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.useCase.AdminDisplayProfessionalDetailUseCase;
import com.ctoutweb.lalamiam.core.useCase.impl.AdminDisplayProfessionalInscriptionDocumentUseCase;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.useCase.AdminDisplayProfessionalToActivateListUseCase;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import com.ctoutweb.lalamiam.infra.core.mapper.ProfessionalToActivateDetailMapper;
import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateDetailDto;
import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateResumeDto;
import com.ctoutweb.lalamiam.infra.mapper.ProfessionalToActivateResumeMapper;
import com.ctoutweb.lalamiam.infra.service.IAdminService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {
  private final CoreFactory factory;
  private final AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase;
  private final AdminDisplayProfessionalToActivateListUseCase adminDisplayProfessionalToActivateListUseCase;
  private final AdminDisplayProfessionalDetailUseCase adminDisplayProfessionalDetailUseCase;
  private final ProfessionalToActivateResumeMapper professionalToActivateResumeMapper;
  private final ProfessionalToActivateDetailMapper professionalToActivateDetailMapper;

  public AdminServiceImpl(
          CoreFactory factory, AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase,
          AdminDisplayProfessionalToActivateListUseCase adminDisplayProfessionalToActivateListUseCase,
          AdminDisplayProfessionalDetailUseCase adminDisplayProfessionalDetailUseCase, ProfessionalToActivateResumeMapper professionalToActivateResumeMapper, ProfessionalToActivateDetailMapper professionalToActivateDetailMapper) {
    this.factory = factory;
    this.adminDisplayProfessionalInscriptionDocumentUseCase = adminDisplayProfessionalInscriptionDocumentUseCase;
    this.adminDisplayProfessionalToActivateListUseCase = adminDisplayProfessionalToActivateListUseCase;
    this.adminDisplayProfessionalDetailUseCase = adminDisplayProfessionalDetailUseCase;
    this.professionalToActivateResumeMapper = professionalToActivateResumeMapper;
    this.professionalToActivateDetailMapper = professionalToActivateDetailMapper;
  }

  @Override
  @Transactional
  public List<ProfessionalToActivateResumeDto> findProfessionalsToActivates() {
    AdminDisplayProfessionalToActivateListUseCase.Input input = AdminDisplayProfessionalToActivateListUseCase.Input.getInput();
    AdminDisplayProfessionalToActivateListUseCase.Output output = adminDisplayProfessionalToActivateListUseCase.execute(input);

   return output
            .getOutputBoundary()
            .getProfessionalToActivateList()
            .stream()
            .map(professionalToActivateResumeMapper::map)
            .toList();
  }

  @Override
  @Transactional
  public ProfessionalToActivateDetailDto findProfessionalDetail(String professionalEmail) {
    var input = AdminDisplayProfessionalDetailUseCase
            .Input
            .getInput(factory.getAdminDisplayProfessionalDetailInput(professionalEmail));

    var output = adminDisplayProfessionalDetailUseCase.execute(input);
    IProfessionalDetail professional = output.getOutputBoundary().getProfessionalDetail();
    return professionalToActivateDetailMapper.map(professional);
  }
}
