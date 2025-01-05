package com.ctoutweb.lalamiam.infra.repository.impl;

import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription;
import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.repository.IProfessionalAccountRepository;
import com.ctoutweb.lalamiam.infra.repository.IProfessionalRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class ProfessionalInscriptionRepositoryImpl implements IProfessionalInscriptionRepository {
  private final IProfessionalRepository professionalRepository;
  private final IProfessionalAccountRepository professionalAccountRepository;
  private final IUserRepository userRepository;
  private final Factory factory;
  @Value("${zone.id}")
  String zoneId;

  public ProfessionalInscriptionRepositoryImpl(
          IProfessionalRepository professionalRepository,
          IProfessionalAccountRepository professionalAccountRepository,
          IUserRepository userRepository, Factory factory) {
    this.professionalRepository = professionalRepository;
    this.professionalAccountRepository = professionalAccountRepository;
    this.userRepository = userRepository;
    this.factory = factory;
  }

  @Override
  public IProfessionalInscription.ICreatedProfessional addProfessional(Long clientId, IBoundariesAdapter.IBoundaryInputAdapter boundaryInputAdapter) {
    ProfessionalEntity professionalEntity = new ProfessionalEntity();

    UserEntity user = userRepository.findById(clientId).orElse(null);
    //user.setId(clientId);
    professionalEntity.setUser(user);
    professionalEntity.setPhone(boundaryInputAdapter.getPhone());
    professionalEntity.setLastName(boundaryInputAdapter.getLastName());
    professionalEntity.setFirstName(boundaryInputAdapter.getFirstName());
    ProfessionalEntity savedProfessionalEntity = professionalRepository.save(professionalEntity);
    return factory.getImpl(savedProfessionalEntity.getId());
  }


  @Override
  public IProfessionalInscription.ICreatedProfessionalAccount addProfessionalAccount(Long professionalId, boolean isUserAccountActif, LocalDateTime activationLimiteDate) {

    ProfessionalAccountEntity professionalAccount = new ProfessionalAccountEntity();
    UserEntity professional = new UserEntity();
    professional.setId(professionalId);

    professionalAccount.setIsAccountActive(false);
    professionalAccount.setAccountActivationLimitDate(activationLimiteDate.atZone(ZoneId.of(zoneId)));
    professionalAccount.setUser(professional);

    ProfessionalAccountEntity createProfessionalAccount = professionalAccountRepository.save(professionalAccount);

    return factory.getCreateProfessionanAccountImpl(createProfessionalAccount.getId());
  }
}
