package com.ctoutweb.lalamiam.infra.core.repository;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivateRepository;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.infra.core.mapper.ProfessionalToActivateMapper;
import com.ctoutweb.lalamiam.infra.repository.IProfessionalAccountRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfessionalToActivateListRepositoryImpl implements IProfessionalToActivateRepository {
  private final IProfessionalAccountRepository professionalAccountRepository;
  private final ProfessionalToActivateMapper professionalToActivateMapper;

  public ProfessionalToActivateListRepositoryImpl(IProfessionalAccountRepository professionalAccountRepository,ProfessionalToActivateMapper professionalToActivateMapper) {
    this.professionalAccountRepository = professionalAccountRepository;
    this.professionalToActivateMapper = professionalToActivateMapper;
  }

  @Override
  public List<IProfessionalToActivate> getProfessionalToActivateList() {
    List<ProfessionalAccountEntity> professionalAccountEntities = professionalAccountRepository.findByIsAccountRegisterConfirmByProfessionalTrueAndIsAccountActiveFalse();
    return professionalAccountEntities.stream().map(professionalToActivateMapper::map).toList();
  }
}
