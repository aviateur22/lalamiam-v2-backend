package entity.captcha.strategy.impl.factory;

import entity.captcha.strategy.ICaptchaStrategy;
import entity.captcha.CaptchaType;
import entity.captcha.strategy.ICaptchaStrategyFactory;

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
