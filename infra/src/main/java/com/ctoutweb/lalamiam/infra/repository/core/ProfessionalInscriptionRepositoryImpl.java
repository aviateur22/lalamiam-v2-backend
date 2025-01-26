package com.ctoutweb.lalamiam.infra.repository.core;

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
import com.ctoutweb.lalamiam.infra.utility.DateUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.LIMIT_DAY_TO_VALIDATE_INSCRIPTION;

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
  public IProfessionalInscription.ICreatedProfessionalAccount addProfessionalAccount(Long professionalId) {

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
}
