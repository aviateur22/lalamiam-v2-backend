package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.IProfessionalInscriptionConfirmation;

import java.util.Optional;

public interface IProfessionalInscriptionConfirmationRepository {

  public Optional<IProfessional> findProfessionalByEmail(String email);
  public Optional<IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken> findProfessionalConfirmationToken(Long professionalId);
  public void confirmProfessionalInscription(Long professionalId);
}
