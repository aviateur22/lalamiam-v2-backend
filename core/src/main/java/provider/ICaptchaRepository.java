package provider;
import entity.captcha.ICaptcha.ISavedResponseId;
import entity.captcha.ICaptcha.IAvailableCaptchaImage;
import entity.cryptographic.CryptographicType;

import java.time.LocalDateTime;
import java.util.List;

public interface ICaptchaRepository {

  /**
   * Pour les captchas de type Image
   * Récupération des données sur les images Captcha disponible
   * @return List<IAvailableCaptchaImage>
   */
  public List<IAvailableCaptchaImage> foundCaptchaImages();
}
