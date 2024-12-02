package com.ctoutweb.lalamiam.infra.model.captcha.impl;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaImage;

public record CaptchaImageImpl(Long id, String name, String path, String response) implements ICaptchaImage {
  @Override
  public Long getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getPath() {
    return path;
  }

  @Override
  public String getResponse() {
    return response;
  }
}
