package com.ctoutweb.lalamiam.infra.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCaptchaResponse(
          @NotNull(message = "{captcha.response.missing}")
          @NotBlank(message = "{captcha.response.missing}")
          String clientResponse,

          @NotNull(message = "{captcha.response.missing}")
          @NotBlank(message = "{captcha.response.missing}")
          String captchaResponse
  ){}
