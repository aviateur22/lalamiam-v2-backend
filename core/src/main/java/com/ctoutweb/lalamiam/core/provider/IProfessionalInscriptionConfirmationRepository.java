package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.useCase.base.adapter.IProfessionalInformation;

public interface IProfessionalInscriptionConfirmationRepository {

  public IProfessionalInformation findProfessionalByEmail(String email);
  public void confirmProfessionalRegister(Long professionalId);
}
