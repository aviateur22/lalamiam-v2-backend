package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription.ICreatedProfessionalAccount;

import java.time.LocalDateTime;


public interface IProfessionalInscriptionRepository {
public  ICreatedProfessional addProfessional (Long clientId);

  public ICreatedProfessionalAccount addProfessionalAccount(
          Long professionalId,
          boolean isUserAccountActif,
          LocalDateTime activationLimiteDate
  );
}
