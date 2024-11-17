package com.ctoutweb.lalamiam.core.factory;

import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategyFactory;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.factory.CaptchaStrategyFactoryImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
import com.ctoutweb.lalamiam.core.provider.ICaptchaRepository;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;

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
