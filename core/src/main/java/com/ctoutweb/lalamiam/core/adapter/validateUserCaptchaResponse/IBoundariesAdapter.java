package com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse;

import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;

public interface IBoundariesAdapter {

  /**
   * Entée du useCase
   */
  public interface IBoundaryInputAdapter {

    /**
     * Réponse utilisateur
     * @return String
     */
    public String getCaptchaResponseByUser();

    /**
     * Hash ou Déchiffrement de la réponse du captcha
     * @return String
     */
    public String getHashOrDecrypteCaptchaResponse();

    /**
     * Type de cryptographie
     * @return CryptographicType
     */
    CryptographicType getCryptographicType();

    /**
     * Captcha token
     * @return
     */
    String getCaptchaToken();

    /**
     * Separator entre le captchaToken et réponse client
     * @return
     */
    String getCaptchaTokenSeparator();
  }
  /**
   * Sortie du useCase
   */
  public interface IBoundaryOutputAdapter {
    boolean getIsClientResponseValid();
  }
}
