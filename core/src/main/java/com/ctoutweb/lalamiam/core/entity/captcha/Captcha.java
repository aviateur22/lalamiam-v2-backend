package com.ctoutweb.lalamiam.core.entity.captcha;

import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography.ICryptographySaveResult;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IAvailableCaptchaImage;
import com.ctoutweb.lalamiam.core.exception.ApplicationException;
import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
import com.ctoutweb.lalamiam.core.provider.ICaptchaRepository;
import com.ctoutweb.lalamiam.core.util.IntegerUtil;
import com.ctoutweb.lalamiam.core.util.TextUtil;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Captcha {
  /**
   * Domaine de la cryptographie
   */
  private final ICaptchaRepository captchaRepository;
  private final ICryptographicService cryptographicService;
  private final ICaptchaConfiguration captchaConfiguration;
  protected Captcha(
          ICaptchaRepository captchaRepository,
          ICryptographicService cryptographicService,
          ICaptchaConfiguration captchaConfiguration
  ) {
    this.captchaRepository = captchaRepository;
    this.cryptographicService = cryptographicService;
    this.captchaConfiguration = captchaConfiguration;
  }

  /**
   * Génération d'un chiffre aléatoire entre un min et un max
   * @return int
   */
  public int generateRandomNumberInBetween(int min, int max) {
    return IntegerUtil.generateNumberBetween(min, max);
  }

  /**
   * Pour captcha type Calcul
   *
   * Sélection d'une opération mathématiqye entre '+', '-', '*'
   * @return char
   */
  public char generateRandomMathOperation() {
    final char[] OPERATIONS = new char[]{'+', '-', '*'};

    // Bornes de OPERATIONS
    int minIndex = 0;
    int maxIndex = OPERATIONS.length - 1;

    // Calcul d'un index aléatoire
    int randomIndex = this.generateRandomNumberInBetween(minIndex, maxIndex);

    return OPERATIONS[randomIndex];
  }

  /**
   * Pour captcha de type Text
   *
   * Génération d'un text aléatoire
   * @return String
   */
  public String generateRandomWord(int wordLength) {
    return TextUtil.generateText(wordLength);
  }

  /**
   * Pour captcha de type Calcul
   *
   * Calcul du resultat
   *
   * @param randomNumber1 int
   * @param randomMathOperation char
   * @param randomNumber2 int
   * @return int - Résulta du calcul
   */
  public int calculateResult(int randomNumber1, char randomMathOperation, int randomNumber2, String messageException) {
    return switch (randomMathOperation) {
      case '+' -> randomNumber1 + randomNumber2;
      case '-' -> randomNumber1 - randomNumber2;
      case '*' -> randomNumber1 * randomNumber2;
      default -> throw new ApplicationException(messageException);
    };
  }

  /**
   * Pour captcha de type Calcul
   *
   * Création de la question du captcha
   *
   * @param randomNumber1 int
   * @param randomMathOperation char
   * @param randomNumber2 int
   * @return String
   */
  public String generateCaptchaQuestion(int randomNumber1, char randomMathOperation, int randomNumber2) {
    return String.format("%02d %c %02d", randomNumber1, randomMathOperation, randomNumber2);
  }

  /**
   * Ajout d'un Token à la réponse générée
   * @return String
   */
  public String addCaptchaTokenToGeneratedResponse(String generatedResponse) {
    String captchaToken = this.captchaConfiguration.getCaptchaToken();
    String stringSeparator = this.captchaConfiguration.getStringSeparator();

    return String.format("%S%s%s",captchaToken,stringSeparator, generatedResponse);
   }

  /**
   * Cryptographie de la réponse du capctaha
   * @param captchaResponse String - Captcha response
   * @return ICryptographySaveResult
   */
  protected ICryptographySaveResult cryptographyCaptchaResponseAnsSave(
          String captchaResponse,
          CryptographicType cryptographicType,
          LocalDateTime limitValidityDate
  ) {
   return  cryptographicService.cryptographyTextAndSave(
           cryptographicType,
           captchaResponse,
           limitValidityDate
   );
  }

  /**
   * Pour les captcha de type Image
   * Récupération de la liste des images captcha de disponible
   * @return List<IAvailableCaptchaImage>
   */
  protected List<IAvailableCaptchaImage> getCaptchaImages() {
    return captchaRepository.foundCaptchaImages();
  }


}
