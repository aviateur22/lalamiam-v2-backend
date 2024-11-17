package com.ctoutweb.lalamiam.core.entity.executeCaptachaStrategy.impl.boundary;

import com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaContext;

public record BoundaryInputImpl(ICaptchaContext captchaContext) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(ICaptchaContext captchaContext) {
    return new BoundaryInputImpl(captchaContext);
  }

  @Override
  public ICaptchaContext getCaptchaContext() {
    return captchaContext;
  }
}
