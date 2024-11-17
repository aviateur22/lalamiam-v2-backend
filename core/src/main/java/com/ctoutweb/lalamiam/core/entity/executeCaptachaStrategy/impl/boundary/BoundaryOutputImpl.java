package com.ctoutweb.lalamiam.core.entity.executeCaptachaStrategy.impl.boundary;

import com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryOutputAdapter;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;

public record BoundaryOutputImpl(IGeneratedCaptcha generatedCaptcha) implements IBoundaryOutputAdapter {
  public static BoundaryOutputImpl getBoundaryOutputImpl(IGeneratedCaptcha generatedCaptcha) {
    return new BoundaryOutputImpl(generatedCaptcha);
  }

  @Override
  public IGeneratedCaptcha getGeneratedCaptcha() {
    return generatedCaptcha;
  }
}
