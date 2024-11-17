package entity.executeCaptachaStrategy.impl.boundary;

import adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter;
import entity.captcha.strategy.ICaptchaContext;

public record BoundaryInputImpl(ICaptchaContext captchaContext) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(ICaptchaContext captchaContext) {
    return new BoundaryInputImpl(captchaContext);
  }

  @Override
  public ICaptchaContext getCaptchaContext() {
    return captchaContext;
  }
}
