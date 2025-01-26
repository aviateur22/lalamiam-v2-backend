package com.ctoutweb.lalamiam.infra.model.core.adapter;

import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalAccountInformation;

import java.time.ZonedDateTime;

public record ProfessionaAccountInformationImpl(
        Long accountId,
        ZonedDateTime accountRegisterConfirmationLimitTime,
        boolean isAccountActivated,
        boolean isRegisterConfirmByProfessional
) implements IProfessionalAccountInformation {
  @Override
  public Long getAccountProfessionalId() {
    return accountId;
  }

  @Override
  public ZonedDateTime getAccountRegisterConfirmationLimitTime() {
    return accountRegisterConfirmationLimitTime;
  }

  @Override
  public Boolean getIsAccountActivated() {
    return isAccountActivated;
  }

  @Override
  public Boolean getIsRegisterConfirmByProfessional() {
    return isRegisterConfirmByProfessional;
  }
}
