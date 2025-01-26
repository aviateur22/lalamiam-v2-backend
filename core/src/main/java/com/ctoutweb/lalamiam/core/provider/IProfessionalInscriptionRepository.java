package com.ctoutweb.lalamiam.core.provider;

import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription.ICreatedProfessionalAccount;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


public interface IProfessionalInscriptionRepository {
public ICreatedProfessional addProfessional (Long clientId, IBoundaryInputAdapter boundaryInputAdapter);

  public ICreatedProfessionalAccount addProfessionalAccount(Long professionalId);
}
