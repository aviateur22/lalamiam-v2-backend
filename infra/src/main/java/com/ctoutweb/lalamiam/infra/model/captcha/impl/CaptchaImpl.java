package com.ctoutweb.lalamiam.infra.model.captcha.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;

public record CaptchaImpl(String captchaTitle, IImageBase64 captchaQuestionImageBase64, Long captchaResponseId) implements ICaptcha {
  @Override
  public String getCaptchaTitle() {
    return captchaTitle;
  }

  @Override
  public Long getCaptchaResponseId() {
    return captchaResponseId;
  }

  @Override
  public IImageBase64 getCaptchaQuestionImageBase64() {
    return captchaQuestionImageBase64;
  }
}
