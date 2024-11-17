package entity.professionalInscriptionConfirmation.impl.entity;

import entity.professionalInscriptionConfirmation.IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken;

import java.time.LocalDateTime;

public record ProfessionalConfirmationTokenImpl(String urlHashtoken, String emailHashToken, LocalDateTime activatedLimitTime)
        implements IProfessionalConfirmationToken {
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
