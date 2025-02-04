package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.gateway;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalInformation;

public interface IProfessionalInscriptionConfirmationRepository {

  public IProfessionalInformation findProfessionalByEmail(String email);
  public void confirmProfessionalRegister(Long professionalId);
}
