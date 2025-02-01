package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.entity.IProfessionalToActivate;

import java.time.ZonedDateTime;

public record ProfessionalToActivateImpl(Long proId, String proEmail, ZonedDateTime accountCreatedAt) implements IProfessionalToActivate {
  @Override
  public Long getProfessionalId() {
    return proId;
  }

  @Override
  public String getProfessionalEmail() {
    return proEmail;
  }

  @Override
  public ZonedDateTime getAccountCreatedAt() {
    return accountCreatedAt;
  }
}
