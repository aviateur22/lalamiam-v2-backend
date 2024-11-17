package entity.captcha.strategy;

import entity.captcha.ICaptcha.IGeneratedCaptcha;

public interface ICaptchaStrategy {
  public IGeneratedCaptcha generateCaptcha();
}
