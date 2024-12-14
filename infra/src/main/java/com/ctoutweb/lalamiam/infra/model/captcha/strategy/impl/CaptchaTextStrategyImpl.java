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

  public CaptchaTextStrategyImpl(ICaptchaGeneration captchaGeneration) {
    this.captchaGeneration = captchaGeneration;
  }

  @Override
  public ICaptcha generateCaptcha() throws IOException {
    return captchaGeneration
            .generate(captchaType)
            .cryptographyAndSaveResponse(cryptoType)
            .convertCaptchaQuestionToBase64Image()
            .getCaptcha();
  }
}
