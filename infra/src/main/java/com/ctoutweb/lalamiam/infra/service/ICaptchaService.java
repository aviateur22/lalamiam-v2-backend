package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;

import java.io.IOException;

/**
 * Gestion des captchas
 */
public interface ICaptchaService {

  /**
   * Renvoi un captacha généré en mode Random
   * @return ICaptcha
   */
  ICaptcha generateRandomCapatcha() throws IOException;
}
