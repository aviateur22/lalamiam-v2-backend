package com.ctoutweb.lalamiam.infra.model.captcha;

/**
 * Abastraction des données images pour les captchas de type image
 */
public interface ICaptchaImage {
  Long getId();

  /**
   * Nom de l'image
   * @return String
   */
  String getName();

  /**
   * Path de l'image
   * @return String
   */
  String getPath();

  /**
   * Réponse associée à l'image
   * @return String
   */
  String getResponse();
}
