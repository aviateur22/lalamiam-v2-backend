package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessionalAccount;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;



public interface IProfessionalInscriptionRepository {
public ICreatedProfessional addProfessional (Long clientId, IProfessionalInscriptionInput professionalInscriptionInput);

  public ICreatedProfessionalAccount addProfessionalAccount(Long professionalId);
}
