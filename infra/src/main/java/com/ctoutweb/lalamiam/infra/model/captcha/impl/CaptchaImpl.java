package com.ctoutweb.lalamiam.infra.model.captcha.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;

public record CaptchaImpl(String captchaTitle, IImageBase64 captchaQuestionImageBase64, String captchaResponseIdEncrypt) implements ICaptcha {
  @Override
  public String getCaptchaTitle() {
    return captchaTitle;
  }

  @Override
  public String getCaptchaResponseIdEncrypt() {
    return captchaResponseIdEncrypt;
  }

  @Override
  public IImageBase64 getCaptchaQuestionImageBase64() {
    return captchaQuestionImageBase64;
  }
}
