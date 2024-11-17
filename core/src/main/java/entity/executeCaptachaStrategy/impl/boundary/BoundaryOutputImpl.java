package entity.executeCaptachaStrategy.impl.boundary;

import adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryOutputAdapter;
import entity.captcha.ICaptcha.IGeneratedCaptcha;

public record BoundaryOutputImpl(IGeneratedCaptcha generatedCaptcha) implements IBoundaryOutputAdapter {
  public static BoundaryOutputImpl getBoundaryOutputImpl(IGeneratedCaptcha generatedCaptcha) {
    return new BoundaryOutputImpl(generatedCaptcha);
  }

  @Override
  public IGeneratedCaptcha getGeneratedCaptcha() {
    return generatedCaptcha;
  }
}
