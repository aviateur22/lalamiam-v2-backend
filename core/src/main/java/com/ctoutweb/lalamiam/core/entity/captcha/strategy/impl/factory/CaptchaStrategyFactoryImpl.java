package com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.factory;

import com.ctoutweb.lalamiam.core.entity.captcha.CaptchaType;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategyFactory;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;

public class CaptchaStrategyFactoryImpl implements ICaptchaStrategyFactory {
  private final ICaptchaStrategy captchaTextStrategy;
  private final ICaptchaStrategy captchaCalculStrategy;
  private final ICaptchaStrategy captchaImageStrategy;

  public CaptchaStrategyFactoryImpl(
          ICaptchaStrategy captchaTextStrategy,
          ICaptchaStrategy captchaCalculStrategy,
          ICaptchaStrategy captchaImageStrategy
  ) {
    this.captchaTextStrategy = captchaTextStrategy;
    this.captchaCalculStrategy = captchaCalculStrategy;
    this.captchaImageStrategy = captchaImageStrategy;
  }

  @Override
  public ICaptchaStrategy getCaptchaStrategy(CaptchaType randomCaptchaType) {
    return switch (randomCaptchaType) {
      case TEXT -> captchaTextStrategy;
      case CALCUL -> captchaCalculStrategy;
      case IMAGE -> captchaImageStrategy;
      default -> null;
    };
  }
}
