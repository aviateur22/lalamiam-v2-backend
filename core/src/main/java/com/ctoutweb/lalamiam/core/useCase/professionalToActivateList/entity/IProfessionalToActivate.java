package com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.entity;

import java.time.ZonedDateTime;

public interface IProfessionalToActivate {
  public Long getProfessionalId();
  public String getProfessionalEmail();
  public ZonedDateTime getAccountCreatedAt();
}
