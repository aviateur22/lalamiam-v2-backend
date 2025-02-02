package com.ctoutweb.lalamiam.infra.mapper;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateResumeDto;
import org.springframework.stereotype.Component;

@Component
public class ProfessionalToActivateResumeMapper {
  public ProfessionalToActivateResumeDto map(IProfessionalToActivate professionalToActivate) {
    return new ProfessionalToActivateResumeDto(
            professionalToActivate.getProfessionalId(),
            professionalToActivate.getProfessionalEmail(),
            professionalToActivate.getAccountCreatedAt());
  }
}
