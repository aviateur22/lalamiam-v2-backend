package com.ctoutweb.lalamiam.infra.service.helper;

import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;

public interface IReinitializeLostPassword  extends IUpdatePassword{

  /**
   * Recherche d'un token de modification mot de passe associé a un email et un le type de crypto utilisé pour proteger le token
   * @param email String
   * @return IReinitializeLostPassword
   */
  public IReinitializeLostPassword findUserToken(String email);

  /**
   * Suppression token de la base de données
   * @return IReinitializeLostPassword
   */
  public IReinitializeLostPassword deleteUserToken();

  /**
   * Vérification si un token de type "Encrypt" est valide
   * @param tokenFromFrontEnd String - Token provenant du frontEnd qui est à verifier
   * @return IReinitializeLostPassword
   */
  public IReinitializeLostPassword isFrontEndTokenValid(String tokenFromFrontEnd);

}
