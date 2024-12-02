package com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory.ICaptchaStrategyFactory;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import org.springframework.stereotype.Component;

@Component
public class CaptchaStrategyFactoryImpl implements ICaptchaStrategyFactory {
  @Override
  public ICaptchaStrategy getCaptchaStrategy(CaptchaType randomCaptchaType) {
    return null;
  }
}
