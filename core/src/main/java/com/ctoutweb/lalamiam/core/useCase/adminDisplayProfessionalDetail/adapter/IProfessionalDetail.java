package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter;

import java.time.ZonedDateTime;
import java.util.List;

public interface IProfessionalDetail {
  public Long getProfessionalId();
  public String getProfessionalEmail();
  public String getPhoneNumber();
  public String getFirstName();
  public String getLastName();
  public List<String> getStatutDocuments();
  ZonedDateTime getAccountCreatedAt();
  ZonedDateTime getAccountRegisterConfirmAt();
  public Boolean getIsAccountActivate();
  public Boolean getIsAccountRegisterConfirm();


}
