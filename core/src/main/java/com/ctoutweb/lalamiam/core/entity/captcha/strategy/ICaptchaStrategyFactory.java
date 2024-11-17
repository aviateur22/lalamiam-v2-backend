package com.ctoutweb.lalamiam.core.entity.captcha.strategy;

import com.ctoutweb.lalamiam.core.entity.captcha.CaptchaType;

public interface ICaptchaStrategyFactory {

  /**
   * Renvoie la stratégie Captcha a utiliser
   * @param randomCaptchaType CaptchaType - Type de captcha a utiliser
   * @return ICaptchaStrategy
   */
  public ICaptchaStrategy getCaptchaStrategy(CaptchaType randomCaptchaType);
}
