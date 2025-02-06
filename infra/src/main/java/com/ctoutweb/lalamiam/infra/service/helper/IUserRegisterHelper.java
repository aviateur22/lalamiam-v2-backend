package com.ctoutweb.lalamiam.infra.service.helper;

public interface IUserRegisterHelper {
  /**
   * Recherche des données du professionenl
   * @param email String
   * @return IUserRegisterHelper
   */
  public IUserRegisterHelper findUserInformation(String email);

  /**
   * Génération des Tokens lors de l'inscription d'un professionel
   * Les tokens seront sauvegardés en base afin de pouvoir controler l'inscription d'un professionel
   *
   * @return IUserRegisterHelper
   */
  public IUserRegisterHelper generateTokenRegistration();

  /**
   * Cryptography des tokens générés
   * @return IUserRegisterHelper
   */
  public IUserRegisterHelper tokenCryptography();


  /**
   * Sauvegarde du chiffrement du token de url
   * @return IUserRegisterHelper
   */
  public IUserRegisterHelper saveGeneratedEncryptTokenWithUser();


  /**
   * Envoie de l'email demandant la confirmation de l'inscription
   * @param professionalEmail String - Adress pour envoie de l'email
   */
  public void sendRegisterEmail(String professionalEmail);
}
