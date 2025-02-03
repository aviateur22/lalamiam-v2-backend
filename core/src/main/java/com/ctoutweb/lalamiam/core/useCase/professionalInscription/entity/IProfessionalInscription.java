package com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessioanlInscriptionOutput;

public interface IProfessionalInscription {
  /**
   * Vérification si un professionnel n'est pas déja enregistré
   * @return IProfessionalInscription
   */
  public IProfessionalInscription isProfessionalEmailAvailable();

  /**
   * Création d'un compte User
   * @return IProfessionalInscription
   */
  public IProfessionalInscription createUserAccount();

  /**
   * Création d'un professionel
   * @return IProfessionalInscription
   */
  public IProfessionalInscription registerProfessional();

  /**
   * Sauvegarde des documents d'inscription
   * @return IProfessionalInscription
   */
  public IProfessionalInscription saveProfessionalInscriptionDocument();

  /**
   * Création d'un compte profesionel
   * @return IProfessionalInscription
   */
  public IProfessionalInscription createProfessionalAccount();

  /**
   * Réponse du UseCase
   * @return IProfessioanlInscriptionOutput
   */
  public IProfessioanlInscriptionOutput getProfessionalInscriptionResponse();
}
