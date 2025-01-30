package com.ctoutweb.lalamiam.infra.service.helper;

public interface IProfessionalRegisterConfirmationHelper {

  /**
   * Recherche d'un token d'inscription d'un professionel avec email et un le type de crypto utilisé pour proteger le token
   * @param email String - Email du professionel
   * @return IProfessionalRegisterConfirmationHelper
   */
  public IProfessionalRegisterConfirmationHelper findUserRegisterToken(String email);

  /**
   * Vérification si token email valide
   * @param emailTokenFromFrontEnd String
   * @return IProfessionalRegisterConfirmationHelper
   */
  public IProfessionalRegisterConfirmationHelper isEmailTokenValid(String emailTokenFromFrontEnd);

  /**
   * Vérification si urlToken valide
   * @param urlTokenFromFrontEnd String
   * @return IProfessionalRegisterConfirmationHelper
   */
  public IProfessionalRegisterConfirmationHelper isUrlTokenValid(String urlTokenFromFrontEnd);

  /**
   * Suppression des tokens en cas de succes
   * @return IProfessionalRegisterConfirmationHelper
   */
  public IProfessionalRegisterConfirmationHelper deleteTokenOnSuccess();

  /**
   * Renvoie si la validation des tokens
   * @return boolean
   */
  public Boolean areTokensValid();
}
