package com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory;

import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;

/**
 * Stratégie captcha a utiliser
 */
public interface ICaptchaStrategyFactory {
  public ICaptchaStrategy getCaptchaStrategy(CaptchaType randomCaptchaType);
}
