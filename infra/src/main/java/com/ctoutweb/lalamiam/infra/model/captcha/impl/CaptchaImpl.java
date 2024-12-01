package com.ctoutweb.lalamiam.infra.model.captcha.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.BaseCapatcha;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.image.IBase64Image;

public record CaptchaImpl(BaseCapatcha baseCapatcha) implements ICaptcha {
  @Override
  public String getCaptchaTitle() {
    return null;
  }

  @Override
  public Long getResponseId() {
    return null;
  }

  @Override
  public IBase64Image getCaptchaQuestionBase64() {
    return null;
  }
}
