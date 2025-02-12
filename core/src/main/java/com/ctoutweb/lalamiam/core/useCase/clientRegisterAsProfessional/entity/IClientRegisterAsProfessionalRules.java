package com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.entity;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;

public interface IClientRegisterAsProfessionalRules {
  /**
   * Création d'un compte User
   * @return IProfessionalInscription
   */
  public IClientRegisterAsProfessionalRules isClientProfessional(IProfessionalInscriptionInput professionalInscriptionInformation);

  /**
   * Création d'un professionel
   * @return IProfessionalInscription
   */
  public IClientRegisterAsProfessionalRules registerProfessional();

  /**
   * Sauvegarde des documents d'inscription
   * @return IProfessionalInscription
   */
  public IClientRegisterAsProfessionalRules saveProfessionalInscriptionDocument();

  /**
   * Création d'un compte profesionel
   * @return IProfessionalInscription
   */
  public IClientRegisterAsProfessionalRules createProfessionalAccount();

  /**
   * Envoie d'un email pour demander la confirmation d'inscription
   * @return IClientRegisterAsProfessionalRules
   */
  public IClientRegisterAsProfessionalRules sendRegisterConfirmationEmail();

  /**
   * Réponse du UseCase
   * @return IProfessioanlInscriptionOutput
   */
  public IProfessioanlInscriptionOutput getProfessionalInscriptionResponse();
}
