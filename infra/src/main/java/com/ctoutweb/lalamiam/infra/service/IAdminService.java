package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.dto.admin.activateProfessional.ProfessionalToActivateResumeDto;

import java.util.List;

public interface IAdminService {

  /**
   * Récupération d'un liste de professionel dont le compte est a activer
   * @return List<ProfessionalToActivateResumeDto>
   */
  public List<ProfessionalToActivateResumeDto> findProfessionalsToActivates();
}
