package entity.randomCaptchaSelection.impl;

import adapter.randomCaptchaSelection.IBoundaryOutputAdapter;
import entity.captcha.strategy.ICaptchaContext;

public record BoundaryOutputImpl(ICaptchaContext captchaContext) implements IBoundaryOutputAdapter {

  public static BoundaryOutputImpl getBoundaryOutputImpl(ICaptchaContext captchaContext) {
    return new BoundaryOutputImpl(captchaContext);
  }
  
  @Override
  public ICaptchaContext getCaptchaContext() {
    return captchaContext;
  }
}
