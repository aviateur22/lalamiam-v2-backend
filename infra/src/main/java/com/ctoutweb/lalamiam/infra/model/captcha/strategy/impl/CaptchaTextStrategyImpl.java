package com.ctoutweb.lalamiam.infra.model.captcha.strategy.impl;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaGeneration;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("CaptchaTextStrategy")
public class CaptchaTextStrategyImpl implements ICaptchaStrategy {
  private static final CaptchaType captchaType = CaptchaType.TEXT;
  private static final CryptographyType cryptoType = CryptographyType.HASH;
  private final ICaptchaGeneration captchaGeneration;
  private final Factory factory;

  public CaptchaTextStrategyImpl(ICaptchaGeneration captchaGeneration, Factory factory) {
    this.captchaGeneration = captchaGeneration;
    this.factory = factory;
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
