package com.ctoutweb.lalamiam.core.entity.randomCaptchaSelection.impl;

import com.ctoutweb.lalamiam.core.adapter.randomCaptchaSelection.IBoundaryOutputAdapter;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaContext;

public record BoundaryOutputImpl(ICaptchaContext captchaContext) implements IBoundaryOutputAdapter {

  public static BoundaryOutputImpl getBoundaryOutputImpl(ICaptchaContext captchaContext) {
    return new BoundaryOutputImpl(captchaContext);
  }
  
  @Override
  public ICaptchaContext getCaptchaContext() {
    return captchaContext;
  }
}
