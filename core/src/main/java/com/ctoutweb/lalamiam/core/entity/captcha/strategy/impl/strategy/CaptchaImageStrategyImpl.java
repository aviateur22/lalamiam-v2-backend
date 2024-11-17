package com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy;

import com.ctoutweb.lalamiam.core.entity.captcha.Captcha;
import com.ctoutweb.lalamiam.core.entity.captcha.impl.GeneratedCaptchaImpl;
import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography;
import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
import com.ctoutweb.lalamiam.core.provider.ICaptchaRepository;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IAvailableCaptchaImage;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class CaptchaImageStrategyImpl extends Captcha implements ICaptchaStrategy {
  private final IMessageService messageService;

  /**
   * Type de cryptographie utilisée pour la réponse du captcha
   */
  final CryptographicType CRYPTOGRAPHY_TYPE = CryptographicType.ENCRYPTION;

  /**
   * La génération de la question du captcha est-elle à transformer en image
   */
  final boolean IS_GENERATED_QUESTION_TO_BE_TRANSFORM_IN_IMAGE = false;



  public CaptchaImageStrategyImpl(
          IMessageService messageService,
          ICryptographicService cryptographicService,
          ICaptchaRepository captchaRepository,
          ICaptchaConfiguration captchaConfiguration
  ) {
    super(captchaRepository, cryptographicService, captchaConfiguration);
    this.messageService = messageService;
  }

  @Override
  public IGeneratedCaptcha generateCaptcha() {

    // Titre du captcha
    final String CAPTCHA_TITLE = this.messageService.getMessage("captcha.image.title");


    // Validité du captcha
    LocalDateTime validityResponseDateTime = LocalDateTime.now().plusHours(1);


    // Récupération des données des images disponibles
    List<IAvailableCaptchaImage> availableCaptchaImages = this.getAvailableCaptchaImages();

    // Sortie de la génération du captcha si pas de données
    if(availableCaptchaImages.isEmpty()) {
      return null;
    }

    // Récupération aléatoire d'une données dans la liste
    IAvailableCaptchaImage randomCaptchaImage = this.getRandomCaptchaImageInformation(availableCaptchaImages);

    // Cryptographie de la réponse
    String captchaResponse = randomCaptchaImage.getCaptchaResponse();
    ICryptography.ICryptographySaveResult cryptographyCaptchaResponse = this.cryptographyCaptchaResponseAnsSave(
            captchaResponse,
            CRYPTOGRAPHY_TYPE,
            validityResponseDateTime
    );

    // Path image a utiliser
    String questionImagePath = randomCaptchaImage.getImagePath();

    // Donnnées du captcha
    return GeneratedCaptchaImpl.getGenerateCaptchaImpl(
            CAPTCHA_TITLE,
            cryptographyCaptchaResponse.getResponseId(),
            questionImagePath,
            IS_GENERATED_QUESTION_TO_BE_TRANSFORM_IN_IMAGE
    );
  }

  /**
   * Récupération de la liste des images captcha de disponible
   * @return List<IAvailableCaptchaImage>
   */
  public List<IAvailableCaptchaImage> getAvailableCaptchaImages() {
    return this.getCaptchaImages();
  }

  /**
   * Récupération aléatoire des données de une image
   * @param availableCaptchaImages List<IAvailableCaptchaImage>
   * @return IAvailableCaptchaImage
   */
  public IAvailableCaptchaImage getRandomCaptchaImageInformation(List<IAvailableCaptchaImage> availableCaptchaImages) {

    int min = 0;
    int max = availableCaptchaImages.size() - 1;

    // Récupération d'un chiffre aléatoire entre min et max
    int randomIndex = this.generateRandomNumberInBetween(min, max);

    return availableCaptchaImages.get(randomIndex);
  }
}
