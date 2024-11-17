package com.ctoutweb.lalamiam.core.provider;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IAvailableCaptchaImage;

import java.util.List;

public interface ICaptchaRepository {

  /**
   * Pour les captchas de type Image
   * Récupération des données sur les images Captcha disponible
   * @return List<IAvailableCaptchaImage>
   */
  public List<IAvailableCaptchaImage> foundCaptchaImages();
}
