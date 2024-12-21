package com.ctoutweb.lalamiam.infra.model.auth;

import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;

public record UseCaptchaResponseImpl(String userResponse, Long captchaId) implements IUserCaptchaResponse {
  @Override
  public String getCaptchaResponseByUser() {
    return userResponse;
  }

  @Override
  public Long getCaptchaResponseId() {
    return captchaId;
  }
}
