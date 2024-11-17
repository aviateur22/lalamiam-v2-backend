package entity.captcha.strategy.impl.context;

import entity.captcha.ICaptcha.IGeneratedCaptcha;
import entity.captcha.strategy.ICaptchaContext;
import entity.captcha.strategy.ICaptchaStrategy;

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
