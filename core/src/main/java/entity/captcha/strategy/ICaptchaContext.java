package entity.captcha.strategy;

import entity.captcha.ICaptcha.IGeneratedCaptcha;

public interface ICaptchaContext {

  /**
   * 2execution de la stratégie
   * @return IGeneratedCaptcha
   */
  public IGeneratedCaptcha execute();

  /**
   * Selection du type de captcha a executer
   * @param captchaStrategy ICaptchaStrategy
   */
  public void setCaptchaStrategy(ICaptchaStrategy captchaStrategy);

  public ICaptchaStrategy getCaptchaStrategy();
}
