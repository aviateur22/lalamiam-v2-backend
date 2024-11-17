package factory;

import entity.captcha.strategy.ICaptchaStrategyFactory;
import entity.captcha.strategy.impl.factory.CaptchaStrategyFactoryImpl;
import entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
import entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import entity.captcha.strategy.ICaptchaStrategy;
import provider.ICaptchaConfiguration;
import provider.ICaptchaRepository;
import provider.ICryptographicService;
import provider.IMessageService;

public class CaptchaStrategyFactory {

  /**
   * Renvoie les différents types de captcha disponible
   * @param messageService IMessageService
   * @param cryptographicService ICryptographicService
   * @param captchaRepository ICaptchaRepository
   * @return CaptchaStrategyFactory
   */
  public static ICaptchaStrategyFactory getCaptchaStrategyFactory(
          IMessageService messageService,
          ICryptographicService cryptographicService,
          ICaptchaRepository captchaRepository,
          ICaptchaConfiguration captchaConfiguration

  ) {
    ICaptchaStrategy captchatextStrategy = new CaptchaTextStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration);
    ICaptchaStrategy captchaCalculStrategy = new CaptchaCalculStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration);
    ICaptchaStrategy captchaImageStrategy = new CaptchaImageStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration);

    return new CaptchaStrategyFactoryImpl(captchatextStrategy, captchaCalculStrategy, captchaImageStrategy);
  }
}
