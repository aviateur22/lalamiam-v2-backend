package com.ctoutweb.lalamiam.infra.model.auth;

import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;

public record UserCaptchaResponseImpl(String userResponse, String captchaResponseIdEncrypt) implements IUserCaptchaResponse {
  @Override
  public String getCaptchaResponseByUser() {
    return userResponse;
  }

  @Override
  public String getCaptchaResponseIdEncrypt() {
    return captchaResponseIdEncrypt;
  }
}
