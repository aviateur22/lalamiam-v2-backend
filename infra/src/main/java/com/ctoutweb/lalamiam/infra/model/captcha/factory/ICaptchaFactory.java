package com.ctoutweb.lalamiam.infra.model.captcha.factory;

import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;

/**
 * Stratégie captcha
 */
public interface ICaptchaFactory {
  public ICaptchaStrategy getCaptchaStrategy(CaptchaType randomCaptchaType);
}
