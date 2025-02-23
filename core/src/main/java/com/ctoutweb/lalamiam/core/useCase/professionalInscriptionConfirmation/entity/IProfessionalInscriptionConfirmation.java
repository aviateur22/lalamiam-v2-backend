package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.entity;

import java.time.ZonedDateTime;

public interface IProfessionalInscriptionConfirmation {
  public IProfessionalInscriptionConfirmation findProfessionalEmail();
  public IProfessionalInscriptionConfirmation isRegisterConfirmedByProfessional();
  public IProfessionalInscriptionConfirmation isProfessionalAccountActive();
  public IProfessionalInscriptionConfirmation isConfirmationDateValid(ZonedDateTime actualDate);
  public void persistProfessionalInscriptionConfirmation();

}
