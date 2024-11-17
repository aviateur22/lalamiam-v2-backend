package entity.common.impl;

import entity.common.IProfessional;

import java.time.LocalDateTime;

public record ProfessionalImpl(IProfessional professionalAdapter) implements IProfessional {
  @Override
  public Long getProfessionalId() {
    return professionalAdapter.getProfessionalId();
  }

  @Override
  public Long getClientId() {
    return professionalAdapter.getClientId();
  }

  @Override
  public boolean isProfessionalAccountConfirmed() {
    return professionalAdapter.isProfessionalAccountConfirmed();
  }

  @Override
  public boolean isProfessionalAccountValidateByAdmin() {
    return professionalAdapter.isProfessionalAccountValidateByAdmin();
  }

  @Override
  public LocalDateTime professionalAccountActivationAt() {
    return professionalAdapter.professionalAccountActivationAt();
  }
}
