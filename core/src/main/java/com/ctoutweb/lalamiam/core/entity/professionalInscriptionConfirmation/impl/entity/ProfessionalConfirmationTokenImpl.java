package com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.impl.entity;

import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.IProfessionalInscriptionConfirmation;

import java.time.LocalDateTime;

public record ProfessionalConfirmationTokenImpl(String urlHashtoken, String emailHashToken, LocalDateTime activatedLimitTime)
        implements IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken {
  @Override
  public String getUrlHashToken() {
    return urlHashtoken;
  }

  @Override
  public String getEmailHashToken() {
    return emailHashToken;
  }

  @Override
  public LocalDateTime getActivatedLimitTime() {
    return activatedLimitTime;
  }
}
