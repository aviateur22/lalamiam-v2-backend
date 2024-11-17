package entity.professionalInscriptionConfirmation;

import java.time.LocalDateTime;

public interface IProfessionalInscriptionConfirmation {
  public interface IProfessionalConfirmationToken {
    public String getUrlHashToken();
    public String getEmailHashToken();
    public LocalDateTime getActivatedLimitTime();
  }
}
