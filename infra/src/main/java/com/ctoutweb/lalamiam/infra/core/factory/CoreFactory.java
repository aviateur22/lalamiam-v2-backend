package com.ctoutweb.lalamiam.infra.core.factory;

import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailInput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalInformation;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.infra.core.model.*;
import com.ctoutweb.lalamiam.infra.dto.RegisterConfirmByProfessionalDto;
import com.ctoutweb.lalamiam.infra.model.impl.CreateProfessionalImpl;
import com.ctoutweb.lalamiam.infra.model.impl.CreatedAccountImpl;
import com.ctoutweb.lalamiam.infra.model.impl.CreatedClientImpl;
import com.ctoutweb.lalamiam.infra.model.impl.CreatedRoleImpl;
import com.ctoutweb.lalamiam.infra.repository.entity.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class CoreFactory {

  public IClientInscription.ICreatedClient getImlpl(UserEntity user) {
    return new CreatedClientImpl(user);
  }
  public IProfessionalInscription.ICreatedProfessional getImpl(Long professionalId) {
    return new CreateProfessionalImpl(professionalId);
  }

  public IProfessionalInscription.ICreatedProfessionalAccount getCreateProfessionanAccountImpl(Long accoundId) {
    return new CreatedProfessionalAccountImpl(accoundId);
  }

  public IClientInscription.ICreatedRole getImpl(RoleUserEntity roleUser) {
    return new CreatedRoleImpl(roleUser);
  }

  public IClientInscription.ICreatedAccount getImpl(UserAccountEntity userAccount)  {
    return new CreatedAccountImpl(userAccount);
  }

  public IProfessionalAccountInformation getProfessionalAccountInformation(
          Long accountId,
          ZonedDateTime accountRegisterConfirmationLimitTime,
          boolean isAccountActivated,
          boolean isRegisterConfirmByProfessional) {
    return new ProfessionaAccountInformationImpl(
            accountId,
            accountRegisterConfirmationLimitTime,
            isAccountActivated,
            isRegisterConfirmByProfessional
    );
  }
  public IProfessionalInformation getProfessionalInformationImpl(Long professionalId, String email, IProfessionalAccountInformation professionalAccountInformation) {
    return new ProfessionalInformationImpl(professionalId, email, professionalAccountInformation);
  }


  public IProfessionalInscriptionConfirmationInput getProfessionalInscriptionConfirmationInputImpl(RegisterConfirmByProfessionalDto dto) {
    return new ProfessionalRegisterConfirmationInputBoundaryImpl(dto.email());
  }

  public IProfessionalToActivate getProfessionalToActivateImpl(String email, Long id, ZonedDateTime createdAt) {
    return new ProfessionalToActivateImpl(id, email, createdAt);
  }

  public IAdminDisplayProfessionalDetailInput getAdminDisplayProfessionalDetailInput(String email) {
    return new AdminDisplayProfessionalDetailInputImpl(email);
  }

  public IProfessionalDetail getProfessionalDetailImpl(
          String professionalEmail,
          ProfessionalEntity professional,
          ProfessionalAccountEntity professionalAccount,
          List<DocumentEntity> documents) {
    return new ProfessionalDetailImpl(professionalEmail, professional, professionalAccount, documents);
  }
}
