package provider;

import entity.common.IProfessional;
import entity.professionalInscriptionConfirmation.IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken;

import java.util.Optional;

public interface IProfessionalInscriptionConfirmationRepository {

  public Optional<IProfessional> findProfessionalByEmail(String email);
  public Optional<IProfessionalConfirmationToken> findProfessionalConfirmationToken(Long professionalId);
  public void confirmProfessionalInscription(Long professionalId);
}
