package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.infra.repository.entity.DocumentEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalEntity;

import java.time.ZonedDateTime;
import java.util.List;

public record ProfessionalDetailImpl(
        String professionalEmail,
        ProfessionalEntity professional,
        ProfessionalAccountEntity professionalAccount,
        List<DocumentEntity> documents) implements IProfessionalDetail {
  @Override
  public Long getProfessionalId() {
    return professional.getId();
  }

  @Override
  public String getProfessionalEmail() {
    return professionalEmail;
  }

  @Override
  public String getPhoneNumber() {
    return professional.getPhone();
  }

  @Override
  public String getFirstName() {
    return professional.getFirstName();
  }

  @Override
  public String getLastName() {
    return professional.getLastName();
  }

  @Override
  public List<String> getStatutDocuments() {
    return documents.stream().map(DocumentEntity::getDocumentPath).toList();
  }

  @Override
  public ZonedDateTime getAccountCreatedAt() {
    return professionalAccount.getCreatedAt();
  }

  @Override
  public ZonedDateTime getAccountRegisterConfirmAt() {
    return professionalAccount.getAccountRegisterConfirmationAt();
  }

  @Override
  public Boolean getIsAccountActivate() {
    return professionalAccount.getIsAccountActive();
  }

  @Override
  public Boolean getIsAccountRegisterConfirm() {
    return professionalAccount.getIsAccountRegisterConfirmByProfessional();
  }
}
