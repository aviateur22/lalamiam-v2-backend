package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory.ICaptchaStrategyFactory;
import com.ctoutweb.lalamiam.infra.service.ICaptchaService;
import com.ctoutweb.lalamiam.infra.utility.IntegerUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CaptchaServiceImpl implements ICaptchaService {

  private final ICaptchaStrategyFactory captchaFactory;

  public CaptchaServiceImpl(ICaptchaStrategyFactory captchaFactory) {
    this.captchaFactory = captchaFactory;
  }

  @Override
  public ICaptcha generateRandomCapatcha() throws IOException {

    CaptchaType randomCaptchaType = getRandomCaptcha();
    return  captchaFactory
            .getCaptchaStrategy(randomCaptchaType)
            .generateCaptcha();
  }

  /**
   * Récupération d'un type de captcha en Random
   * @return CaptchaType
   */
  public CaptchaType getRandomCaptcha() {
    int randomSelection = IntegerUtil.generateNumberBetween(0, CaptchaType.values().length - 1);
    return CaptchaType.values()[randomSelection];
  }
}
