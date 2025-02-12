package com.ctoutweb.lalamiam.infra.dto.auth;

import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserCaptchaResponseDto implements IUserCaptchaResponse {
  @NotNull(message = "{captcha.response.missing}")
  @NotBlank(message = "{captcha.response.missing}")
  String clientResponse;

  @NotNull(message = "{captcha.response.missing}")
  @NotBlank(message = "{captcha.response.missing}")
  String captchaResponseIdEncrypt;


  ////////////////////////////////////////

  public UserCaptchaResponseDto() {
  }

  public UserCaptchaResponseDto(String clientResponse, String captchaResponseIdEncrypt) {
    this.clientResponse = clientResponse;
    this.captchaResponseIdEncrypt = captchaResponseIdEncrypt;
  }

  @Override
  public String getCaptchaResponseByUser() {
    return clientResponse;
  }

  @Override
  public String getCaptchaResponseIdEncrypt() {
    return captchaResponseIdEncrypt;
  }

  public String getClientResponse() {
    return clientResponse;
  }

  public void setClientResponse(String clientResponse) {
    this.clientResponse = clientResponse;
  }

  public void setCaptchaResponseIdEncrypt(String captchaResponseIdEncrypt) {
    this.captchaResponseIdEncrypt = captchaResponseIdEncrypt;
  }
}
