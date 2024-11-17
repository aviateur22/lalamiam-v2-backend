package adapter.randomCaptchaSelection;

import entity.captcha.strategy.ICaptchaContext;

public interface IBoundaryOutputAdapter {
  public ICaptchaContext getCaptchaContext();
}
