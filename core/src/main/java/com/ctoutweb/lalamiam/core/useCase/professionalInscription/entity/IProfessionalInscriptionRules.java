package com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;

public interface IProfessionalInscriptionRules {

  /**
   * Création d'un compte User
   * @return IProfessionalInscription
   */
  public IProfessionalInscriptionRules createUserAccount(IProfessionalInscriptionInput professionalInscriptionInformation);

  /**
   * Création d'un professionel
   * @return IProfessionalInscription
   */
  public IProfessionalInscriptionRules registerProfessional();

  /**
   * Sauvegarde des documents d'inscription
   * @return IProfessionalInscription
   */
  public IProfessionalInscriptionRules saveProfessionalInscriptionDocument();

  /**
   * Création d'un compte profesionel
   * @return IProfessionalInscription
   */
  public IProfessionalInscriptionRules createProfessionalAccount();

  /**
   * Envoie d'un email pour demander la confirmation d'inscription
   * @return IProfessionalInscriptionRules
   */
  public IProfessionalInscriptionRules sendRegisterConfirmationEmail();

  /**
   * Réponse du UseCase
   * @return IProfessioanlInscriptionOutput
   */
  public IProfessioanlInscriptionOutput getProfessionalInscriptionResponse();
}
