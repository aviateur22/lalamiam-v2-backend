package com.ctoutweb.lalamiam.infra.core.repository;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailRepository;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.infra.core.factory.CoreFactory;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.DocumentEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDisplayProfessionalDetailRepositoryImpl implements IAdminDisplayProfessionalDetailRepository {
  private final IUserRepository userRepository;
  private final CoreFactory factory;

  public AdminDisplayProfessionalDetailRepositoryImpl(IUserRepository userRepository, CoreFactory factory) {
    this.userRepository = userRepository;
    this.factory = factory;
  }

  @Override
  public IProfessionalDetail findProfessionalDetail(String professionalEmail) {
    UserEntity user = userRepository.findByEmail(professionalEmail).orElse(null);

    if(user == null)
      return null;

    // Si pas de compte pro
    if(user.getProfessionalInformation() == null)
      return null;

    List<DocumentEntity> documents = user.getProfessionalDocuments();

    ProfessionalAccountEntity professionalAccount = user.getProfessionalAccountInformation();

    String emailProfessional = user.getEmail();

    ProfessionalEntity professional = user.getProfessionalInformation();

    return factory.getProfessionalDetailImpl(professionalEmail, professional, professionalAccount, documents);
  }
}
