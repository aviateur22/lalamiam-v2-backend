package com.ctoutweb.lalamiam.infra.service.helper;

import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;

public interface IProfessionalRegisterConfirmationHelper {

  /**
   * Recherche d'un token d'inscription d'un professionel avec email et un le type de crypto utilisé pour proteger le token
   * @param email String - Email du professionel
   * @param cryptographyType CryptographyType- Type de crypto utilisé
   * @return IProfessionalRegisterConfirmationHelper
   */
  public IProfessionalRegisterConfirmationHelper findUserRegisterToken(String email, CryptographyType cryptographyType);

  /**
   * Vérification si un token de type "Encrypt" est valide
   * @param tokenFromFrontEnd String - Token provenant du frontEnd qui est à verifier
   * @return boolean
   */
  public Boolean isFrontEndTokenValid(String tokenFromFrontEnd);
}
