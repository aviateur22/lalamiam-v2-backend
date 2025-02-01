package com.ctoutweb.lalamiam.infra.core.repository;

import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionConfirmationRepository;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalInformation;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import com.ctoutweb.lalamiam.infra.core.mapper.ProfessionalInformationMapper;
import com.ctoutweb.lalamiam.infra.repository.IProfessionalAccountRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ProfessionalInscriptionConfirmationRepository implements IProfessionalInscriptionConfirmationRepository {

  private final IProfessionalAccountRepository professionalAccountRepository;
  private final IUserRepository userRepository;
  private final ProfessionalInformationMapper professionalInformationMapper;
  private final CoreFactory factory;

  public ProfessionalInscriptionConfirmationRepository(
          IProfessionalAccountRepository professionalAccountRepository,
          IUserRepository userRepository,
          ProfessionalInformationMapper professionalInformationMapper,
          CoreFactory factory) {
    this.professionalAccountRepository = professionalAccountRepository;
    this.userRepository = userRepository;
    this.professionalInformationMapper = professionalInformationMapper;
    this.factory = factory;
  }

  @Override
  public IProfessionalInformation findProfessionalByEmail(String email) {
    UserEntity user = userRepository.findByEmail(email).orElse(null);

    if(user == null || user.getProfessionalInformation() == null || user.getProfessionalAccountInformation() == null)
      return null;

    ProfessionalAccountEntity professionalAccount = user.getProfessionalAccountInformation();

    IProfessionalAccountInformation professionalAccountInformation = factory.getProfessionalAccountInformation(
            professionalAccount.getId(),
            professionalAccount.getAccountRegisterConfirmationLimitDate(),
            professionalAccount.getIsAccountActive(),
            professionalAccount.getIsAccountRegisterConfirmByProfessional()
    );

   return professionalInformationMapper.map(
           user.getId(),
           user.getEmail(),
           professionalAccountInformation
   );
  }

  @Override
  public void confirmProfessionalRegister(Long professionalId) {
    UserEntity user = new UserEntity();
    user.setId(professionalId);

    ProfessionalAccountEntity professionalAccount = professionalAccountRepository.findByUser(user).orElse(null);
    professionalAccount.setIsAccountRegisterConfirmByProfessional(true);
    professionalAccountRepository.save(professionalAccount);
  }
}
