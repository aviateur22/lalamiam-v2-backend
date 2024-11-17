package com.ctoutweb.lalamiam.core.entity.captcha.strategy;

import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha;

public interface ICaptchaStrategy {
  public ICaptcha.IGeneratedCaptcha generateCaptcha();
}
