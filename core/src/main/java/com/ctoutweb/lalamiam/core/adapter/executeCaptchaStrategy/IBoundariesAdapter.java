package com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy;

import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaContext;

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
