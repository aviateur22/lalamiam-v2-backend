package com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory.ICaptchaStrategyFactory;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CaptchaStrategyFactoryImpl implements ICaptchaStrategyFactory {
  private final ICaptchaStrategy captchaCalculStrategy;
  private final ICaptchaStrategy captchaTextStrategy;
  private final ICaptchaStrategy captchaImageStrategy;

  public CaptchaStrategyFactoryImpl(
          @Qualifier("CaptchaCalculStrategy") ICaptchaStrategy captchaCalculStrategy,
          @Qualifier("CaptchaTextStrategy") ICaptchaStrategy captchaTextStrategy,
          @Qualifier("CaptchaImageStrategy") ICaptchaStrategy captchaImageStrategy
  ) {
    this.captchaCalculStrategy = captchaCalculStrategy;
    this.captchaTextStrategy = captchaTextStrategy;
    this.captchaImageStrategy = captchaImageStrategy;
  }

  @Override
  public ICaptchaStrategy getCaptchaStrategy(CaptchaType randomCaptchaType) {

    return switch (randomCaptchaType) {
      case IMAGE -> captchaImageStrategy;
      case TEXT -> captchaTextStrategy;
      case CALCUL -> captchaCalculStrategy;
    };
  }
}
