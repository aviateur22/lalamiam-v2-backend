package adapter.executeCaptchaStrategy;

import entity.captcha.ICaptcha.IGeneratedCaptcha;
import entity.captcha.strategy.ICaptchaContext;

public interface IBoundariesAdapter {

  /**
   * Entrée du UseCase
   */
  public interface IBoundaryInputAdapter {
    ICaptchaContext getCaptchaContext();
  }

  /**
   * Sortie du UseCase
   */
  public interface IBoundaryOutputAdapter {
    IGeneratedCaptcha getGeneratedCaptcha();

  }
}
