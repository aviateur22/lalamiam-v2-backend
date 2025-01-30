package com.ctoutweb.lalamiam.infra.service.helper;

public interface ILostPasswordHelper {
  /**
   * Recherche des données de l'utilisateur
   * @param email String
   * @return ILostPasswordHelper
   */
  public ILostPasswordHelper findUserInformation(String email);

  /**
   * Génération des Tokens autorisant la mise à jour de son mot de passe
   * Les tokens seront sauvegardés en base afin de pouvoir les controler lors de la modification
   *
   * @return IProfessionalRegisterHelper
   */
  public ILostPasswordHelper generateRenewalPasswordToken();

  /**
   * Chiffrement du token générés
   * @return IProfessionalRegisterHelper
   */
  public ILostPasswordHelper encryptToken();

  /**
   * Sauvegarde du chiffrement du token de url
   * @return ILostPasswordHelper
   */
  public ILostPasswordHelper saveGeneratedEncryptTokenWithUser();


  /**
   * Envoie de l'email demandant la confirmation de l'inscription
   * @param professionalEmail String - Adress pour envoie de l'email
   */
  public void sendLostPasswordEmail(String professionalEmail);
}
