package provider;

import entity.professionalInscription.IProfessionalInscription.ICreatedProfessional;
import entity.professionalInscription.IProfessionalInscription.ICreatedProfessionalAccount;

import java.time.LocalDateTime;


public interface IProfessionalInscriptionRepository {
public  ICreatedProfessional addProfessional (Long clientId);

  public ICreatedProfessionalAccount addProfessionalAccount(
          Long professionalId,
          boolean isUserAccountActif,
          LocalDateTime activationLimiteDate
  );
}
