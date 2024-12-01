package com.ctoutweb.lalamiam.infra.model.captcha.factory.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.factory.ICaptchaFactory;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import org.springframework.stereotype.Service;

@Service
public class CaptchaFactoryImpl implements ICaptchaFactory {
  @Override
  public ICaptchaStrategy getCaptchaStrategy(CaptchaType randomCaptchaType) {
    return null;
  }
}
