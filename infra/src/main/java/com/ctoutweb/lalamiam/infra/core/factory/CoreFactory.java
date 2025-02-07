package com.ctoutweb.lalamiam.infra.core.factory;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailInput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalInformation;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedAccount;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedClient;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessionalAccount;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ISavedInscriptionDocuments;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.infra.core.model.*;
import com.ctoutweb.lalamiam.infra.dto.RegisterConfirmByProfessionalDto;
import com.ctoutweb.lalamiam.infra.model.impl.CreateProfessionalImpl;
import com.ctoutweb.lalamiam.infra.model.impl.CreatedAccountImpl;
import com.ctoutweb.lalamiam.infra.model.impl.CreatedClientImpl;
import com.ctoutweb.lalamiam.infra.repository.entity.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class CoreFactory {

  public ICreatedClient getImlpl(Long userId, Long roleId) {
    return new CreatedClientImpl(userId, roleId);
  }
  public ICreatedProfessional getImpl(Long professionalId) {
    return new CreateProfessionalImpl(professionalId);
  }

  public ICreatedProfessionalAccount getCreateProfessionanAccountImpl(Long accoundId) {
    return new CreatedProfessionalAccountImpl(accoundId);
  }
  public ICreatedAccount getImpl(UserAccountEntity userAccount)  {
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

  public ISavedInscriptionDocuments getSavedDocumentImpl(List<Long> documentIds) {
    return new SaveDocumentImpl(documentIds);
  }
}
