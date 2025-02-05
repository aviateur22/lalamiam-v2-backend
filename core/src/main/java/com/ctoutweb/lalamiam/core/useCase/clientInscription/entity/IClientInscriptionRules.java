package com.ctoutweb.lalamiam.core.useCase.clientInscription.entity;

import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionOutput;

public interface IClientInscriptionRules {

  /**
   * Vérification disponibilité de l'email
   * @param clientEmail
   * @return IClientInscriptionRules
   */
  public IClientInscriptionRules isClientEmailAvailable(String clientEmail);

  /**
   * Création Client
   * @param clientInformation IClientInscriptionInput
   * @return IClientInscriptionRules
   */
  public IClientInscriptionRules createClient(IClientInscriptionInput clientInformation);

  /**
   * Creéation du compte client
   * @return IClientInscriptionRules
   */
  public IClientInscriptionRules createClientAccount();

  /**
   * Envoie d'un email d'activation du compte
   * Seule la création d'un compte client entraine l'envoie d'un email
   * @param isEmailToSend boolean
   * @return IClientInscriptionRules
   */
  public IClientInscriptionRules sendEmail(boolean isEmailToSend);

  /**
   * Renvoie des données de l'inscriptiuon client
   * @return IClientInscriptionOutput
   */
  public IClientInscriptionOutput getClientInscriptionInformation();
}
