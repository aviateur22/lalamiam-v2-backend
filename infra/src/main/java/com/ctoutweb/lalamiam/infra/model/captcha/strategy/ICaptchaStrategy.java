package com.ctoutweb.lalamiam.infra.model.captcha.strategy;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;

import java.io.IOException;

/**
 * Abastraction sur la generation d'un nouveau captcha
 */
public interface ICaptchaStrategy {
  /**
   * Genération des données d'un captcha
   * @return ICaptcha
   */
  public ICaptcha generateCaptcha() throws IOException;
}
