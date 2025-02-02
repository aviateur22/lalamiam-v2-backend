package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter;

import java.time.ZonedDateTime;

public interface IProfessionalToActivate {
  public Long getProfessionalId();
  public String getProfessionalEmail();
  public ZonedDateTime getAccountCreatedAt();
}
