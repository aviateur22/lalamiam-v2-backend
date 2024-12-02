package com.ctoutweb.lalamiam.infra.model.captcha.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaGeneration;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;

public record CaptchaImpl(ICaptchaGeneration captchaGeneration) implements ICaptcha {
  @Override
  public String getCaptchaTitle() {
    return captchaGeneration.getCaptchaTitle();
  }

  @Override
  public Long getResponseId() {
    return captchaGeneration.getCaptchaResponseId();
  }

  @Override
  public IImageBase64 getCaptchaQuestionBase64() {
    return captchaGeneration.getCaptchaQuestionBase64();
  }
}
