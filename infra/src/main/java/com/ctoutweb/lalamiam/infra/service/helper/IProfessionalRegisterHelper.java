package com.ctoutweb.lalamiam.infra.service.helper;

/**
 * Gestion de la generation de tokens pour l'envoie d'un email au professionel
 * afin qu'il puisse confirmer son inscription
 */
public interface IProfessionalRegisterHelper {

  /**
   * Recherche des données du professionenl
   * @param email String
   * @return IProfessionalRegisterHelper
   */
  public IProfessionalRegisterHelper findProfessionalInformation(String email);

  /**
   * Génération des Tokens lors de l'inscription d'un professionel
   * Les tokens seront sauvegardés en base afin de pouvoir controler l'inscription d'un professionel
   *
   * @return IProfessionalRegisterHelper
   */
  public IProfessionalRegisterHelper generateTokenForProfessionalRegister();

  /**
   * Cryptography des tokens générés
   * @return IProfessionalRegisterHelper
   */
  public IProfessionalRegisterHelper tokenCryptography();

  /**
   * Sauvegarde du Hash du token de l'email
   * @return IProfessionalRegisterHelper
   */
  public IProfessionalRegisterHelper saveGeneratedHashTokenWithUser();

  /**
   * Sauvegarde du chiffrement du token de url
   * @return IProfessionalRegisterHelper
   */
  public IProfessionalRegisterHelper saveGeneratedEncryptTokenWithUser();


  /**
   * Envoie de l'email demandant la confirmation de l'inscription
   * @param professionalEmail String - Adress pour envoie de l'email
   */
  public void sendRegisterEmail(String professionalEmail);

}
