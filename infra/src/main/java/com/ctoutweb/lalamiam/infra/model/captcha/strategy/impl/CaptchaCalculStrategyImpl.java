package com.ctoutweb.lalamiam.infra.model.captcha.strategy.impl;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaGeneration;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("CaptchaCalculStrategy")
public class CaptchaCalculStrategyImpl implements ICaptchaStrategy {
  private static final CaptchaType captchaType = CaptchaType.CALCUL;
  private static final CryptographyType cryptoType = CryptographyType.HASH;
  private final ICaptchaGeneration captchaGeneration;
  private final Factory factory;

  public CaptchaCalculStrategyImpl(
          Factory factory,
          ICaptchaGeneration captchaGeneration) {
    this.factory = factory;
    this.captchaGeneration = captchaGeneration;
  }

  @Override
  public ICaptcha generateCaptcha() throws IOException {
    captchaGeneration
            .generate(captchaType)
            .cryptographyAndSaveResponse(cryptoType)
            .convertCaptchaQuestionToBase64Image();
    return factory.getImpl(captchaGeneration);
  }
}
