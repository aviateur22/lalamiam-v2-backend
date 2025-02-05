package com.ctoutweb.lalamiam.infra.core.repository;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessionalAccount;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ISavedInscriptionDocuments;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import com.ctoutweb.lalamiam.infra.repository.IProfessionalAccountRepository;
import com.ctoutweb.lalamiam.infra.repository.IProfessionalRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.utility.DateUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.LIMIT_DAY_TO_VALIDATE_INSCRIPTION;

@Component
public class ProfessionalInscriptionRepositoryImpl implements IProfessionalInscriptionRepository {
  private final IProfessionalRepository professionalRepository;
  private final IProfessionalAccountRepository professionalAccountRepository;
  private final IUserRepository userRepository;
  private final CoreFactory factory;
  @Value("${zone.id}")
  String zoneId;

  public ProfessionalInscriptionRepositoryImpl(
          IProfessionalRepository professionalRepository,
          IProfessionalAccountRepository professionalAccountRepository,
          IUserRepository userRepository, CoreFactory factory) {
    this.professionalRepository = professionalRepository;
    this.professionalAccountRepository = professionalAccountRepository;
    this.userRepository = userRepository;
    this.factory = factory;
  }

  @Override
  public ICreatedProfessional addProfessional(Long clientId, IProfessionalInscriptionInput professionalInscriptionInformation) {
    ProfessionalEntity professionalEntity = new ProfessionalEntity();

    UserEntity user = userRepository.findById(clientId).orElse(null);
    //user.setId(clientId);
    professionalEntity.setUser(user);
    professionalEntity.setPhone(professionalInscriptionInformation.getPhone());
    professionalEntity.setLastName(professionalInscriptionInformation.getLastName());
    professionalEntity.setFirstName(professionalInscriptionInformation.getFirstName());
    ProfessionalEntity savedProfessionalEntity = professionalRepository.save(professionalEntity);
    return factory.getImpl(savedProfessionalEntity.getId());
  }


  @Override
  public ICreatedProfessionalAccount addProfessionalAccount(Long professionalId) {

    ProfessionalAccountEntity professionalAccount = new ProfessionalAccountEntity();
    UserEntity professional = new UserEntity();
    professional.setId(professionalId);


    ZonedDateTime accountRegisterConfirmationLimitDate = DateUtility
            .localDateTimeToZonedDateTime(ZoneId.of(zoneId),
                    LocalDateTime.now()
                    .plusDays(LIMIT_DAY_TO_VALIDATE_INSCRIPTION));
    professionalAccount.setIsAccountActive(false);
    professionalAccount.setIsAccountRegisterConfirmByProfessional(false);
    professionalAccount.setAccountRegisterConfirmationLimitDate(accountRegisterConfirmationLimitDate);
    professionalAccount.setUser(professional);

    ProfessionalAccountEntity createProfessionalAccount = professionalAccountRepository.save(professionalAccount);

    return factory.getCreateProfessionanAccountImpl(createProfessionalAccount.getId());
  }

  @Override
  public ISavedInscriptionDocuments saveProfessionalInscriptionDocument(List<File> inscriptionDocuments, Long professionalId) {
    return null;
  }
}
