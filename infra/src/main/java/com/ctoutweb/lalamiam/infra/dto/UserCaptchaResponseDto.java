package com.ctoutweb.lalamiam.infra.dto;

import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCaptchaResponseDto(
          @NotNull(message = "{captcha.response.missing}")
          @NotBlank(message = "{captcha.response.missing}")
          String clientResponse,

          @NotNull(message = "{captcha.response.missing}")
          @NotBlank(message = "{captcha.response.missing}")
          String captchaResponseIdEncrypt
  )  implements IUserCaptchaResponse {
  @Override
  public String getCaptchaResponseByUser() {
    return clientResponse;
  }

  @Override
  public String getCaptchaResponseIdEncrypt() {
    return captchaResponseIdEncrypt;
  }
}
