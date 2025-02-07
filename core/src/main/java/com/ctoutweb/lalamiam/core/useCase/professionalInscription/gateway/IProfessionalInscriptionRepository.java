package com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.ICoreRegisterFile;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;

import java.util.List;

public interface IProfessionalInscriptionRepository {

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
   * @param inscriptionDocuments List<InputStream>
   * @return ISavedInscriptionDocuments
   */
  public ISavedInscriptionDocuments saveProfessionalInscriptionDocument(List<ICoreRegisterFile> inscriptionDocuments, Long professionalId);
}
