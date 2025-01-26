package com.ctoutweb.lalamiam.core.useCase.base.adapter;

import java.time.ZonedDateTime;

public interface IProfessionalAccountInformation {
  Long getAccountProfessionalId();
  ZonedDateTime getAccountRegisterConfirmationLimitTime();
  Boolean getIsAccountActivated();
  Boolean getIsRegisterConfirmByProfessional();
}
