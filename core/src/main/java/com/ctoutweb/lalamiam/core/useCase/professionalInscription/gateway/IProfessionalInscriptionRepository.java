package com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;

import java.io.File;
import java.util.List;

public interface IProfessionalInscriptionRepository {
  /**
   * Réchecrhe existance email professioanel
   * @param professionalEmail String
   * @return Boolean
   */
  public Boolean isProfessionalEmailAvailable (String professionalEmail);

  /**
   * Ajout du professionel
   * @param clientId Long - Id Client
   * @param professionalInscriptionInput IProfessionalInscriptionInput
   * @return ICreatedProfessional
   */
  public ICreatedProfessional addProfessional (Long clientId, IProfessionalInscriptionInput professionalInscriptionInput);

  /**
   * Creation du compte professionel
   * @param professionalId Long
   * @return ICreatedProfessionalAccount
   */
  public ICreatedProfessionalAccount addProfessionalAccount(Long professionalId);

  /**
   * Enregistrement des documents d'inscriptions
   * @param inscriptionDocuments List<File>
   * @return ISavedInscriptionDocuments
   */
  public ISavedInscriptionDocuments saveProfessionalInscriptionDocument(List<File> inscriptionDocuments, Long professionalId);
}
