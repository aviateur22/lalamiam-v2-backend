package com.ctoutweb.lalamiam.infra.model.captcha;

public interface IUserCaptchaResponse {
  /**
   * Réponse utilisateur
   * @return String
   */
  public String getCaptchaResponseByUser();

  /**
   * Id de la réponse
   * @return Long
   */
  public String getCaptchaResponseIdEncrypt();
}
