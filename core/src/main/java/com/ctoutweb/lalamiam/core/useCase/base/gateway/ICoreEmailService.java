package com.ctoutweb.lalamiam.core.useCase.base.gateway;

public interface ICoreEmailService {

  /**
   * Envoie d'un email pour activer un compte d'un client
   * @param clientEmail
   */
  public void sendClientRegisterActivationEmail(String clientEmail);

  /**
   * Envoie d'un email pour demander la confirmation d'inscription a un professionel
   * @param professionalEmail
   */
  public void sendProfessionalRegisterConfirmationEmail(String professionalEmail);

  /**
   * Envoie d'un email pour informer de l'activation d'un compte pro
   * @param professionalEmail
   */
  public void sendActivateProfessionalAccountEmail(String professionalEmail);



}
