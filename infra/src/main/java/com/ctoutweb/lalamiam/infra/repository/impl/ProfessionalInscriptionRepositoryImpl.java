package com.ctoutweb.lalamiam.infra.repository.impl;

import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription;
import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ProfessionalInscriptionRepositoryImpl implements IProfessionalInscriptionRepository {
  @Override
  public IProfessionalInscription.ICreatedProfessional addProfessional(Long clientId) {
    return null;
  }

  @Override
  public IProfessionalInscription.ICreatedProfessionalAccount addProfessionalAccount(Long professionalId, boolean isUserAccountActif, LocalDateTime activationLimiteDate) {
    return null;
  }
}
