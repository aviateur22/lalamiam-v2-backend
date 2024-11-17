package com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.context;

import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaContext;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;

public class CaptchaContextImpl implements ICaptchaContext {
  private ICaptchaStrategy captchaStrategy;

  /**
   * Exécution de la stratégy du captcha
   * @return CaptchaGeneratedInformation
   */
  @Override
  public IGeneratedCaptcha execute() {
   return this.captchaStrategy.generateCaptcha();
  }

  /**
   * Selection du type de captcha a executer
   * @param captchaStrategy CaptchaStrategy
   */
  @Override
  public void setCaptchaStrategy(ICaptchaStrategy captchaStrategy) {
    this.captchaStrategy = captchaStrategy;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////


  @Override
  public ICaptchaStrategy getCaptchaStrategy() {
    return captchaStrategy;
  }
}
