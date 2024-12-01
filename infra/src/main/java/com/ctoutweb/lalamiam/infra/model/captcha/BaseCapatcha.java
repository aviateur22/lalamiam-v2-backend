package com.ctoutweb.lalamiam.infra.model.captcha;

import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.CaptchaImageEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.utility.IntegerUtil;
import com.ctoutweb.lalamiam.infra.utility.TextUtility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.*;

public class BaseCapatcha {
  /**
   * Titre du captcha
   */
  private String captchaTitle;

  /**
   * Question du captcha
   */
  private String captchaQuestion;

  /**
   * Réponse attendu au captcha
   */
  private String captchaResponse;

  /**
   * Id de la réponse en base de données
   */
  private Long captchaResponseId;

  /**
   * Question capycha au format image Base64
   */
  private String captchaQuestionBase64;

  /**
   * Path de l'image à charger si captcha de type Image
   */
  private String imagePath;
  private final IMessageService messageService;
  private final ITokenRepository tokenRepository;
  private final ICryptoService cryptoService;

  public BaseCapatcha(IMessageService messageService, ITokenRepository tokenRepository, ICryptoService cryptoService) {
    this.messageService = messageService;
    this.tokenRepository = tokenRepository;
    this.cryptoService = cryptoService;
  }

  /**
   * Génération des donées captcha pour un captcha type calcul ou text
   * @param captchaType CaptchaType
   * @return BaseCapatcha
   */
  public BaseCapatcha generate(CaptchaType captchaType) {
    switch (captchaType) {
      case TEXT -> captchaTypeText();
      default -> captchaTypeCalcul();
    };
    return this;
  }

  /**
   * Génération des donées captcha pour un captcha type Image
   * @param captchaImages List<CaptchaImageEntity>
   * @return BaseCapatcha
   */
  public BaseCapatcha generate(List<CaptchaImageEntity> captchaImages) {
    captchaTypeImage(captchaImages);
    return this;
  }

  public void captchaTypeImage(List<CaptchaImageEntity> captchaImages) {
    final int MIN = 0;

    captchaTitle = this.messageService.getMessage("captcha.image.title");

    int randomCaptchaIndex = IntegerUtil.generateNumberBetween(MIN, captchaImages.size() - 1);
    CaptchaImageEntity captchaImageSelected = captchaImages.get(randomCaptchaIndex);

    captchaResponse = captchaImageSelected.getResponse();
    imagePath = captchaImageSelected.getPath();
  }

  public void captchaTypeText() {
    final int MIN = 6;
    final int MAX = 11;

    captchaTitle = messageService.getMessage("captcha.text.title");

    captchaQuestion = TextUtility.generateRandomText(IntegerUtil.generateNumberBetween(MIN, MAX));

    // La réponse est équivalente à la question
    captchaResponse = captchaQuestion;
  }

  public void captchaTypeCalcul() {
    final int MIN = 0;
    final int MAX = 9;
    final char[] CALCUl_OPERATION = {'+', '-', '*'};

    // Chiffre 1
    final int RANDOM_NUMBER_1 = IntegerUtil.generateNumberBetween(MIN, MAX);

    // Calcul chiffre 2
    final int RANDOM_NUMBER_2 = IntegerUtil.generateNumberBetween(MIN, MAX);

    // Operation mathématique
    final char RANDOM_MATH_OPERATION =  CALCUl_OPERATION[IntegerUtil.generateNumberBetween(0, CALCUl_OPERATION.length - 1)];

    captchaTitle = messageService.getMessage("captcha.calcul.title");

    captchaResponse = generateCalculResponse(RANDOM_NUMBER_1, RANDOM_NUMBER_2, RANDOM_MATH_OPERATION).toString();

    captchaQuestion = String.format("%02d %c %02d", RANDOM_NUMBER_1, RANDOM_MATH_OPERATION, RANDOM_NUMBER_2);
  }


  /**
   * Calcul la réponse attendu pour un captcha de type Calcul
   * @param randomNumberOne int
   * @param randomNumberTwo int
   * @param randomMathOperation char
   * @return Integer - La réponse au calcul
   */
  private Integer generateCalculResponse(int randomNumberOne, int randomNumberTwo, char randomMathOperation) {
    return  switch (randomMathOperation) {
      case '+' -> randomNumberOne + randomNumberTwo;
      case '-' -> randomNumberOne - randomNumberTwo;
      case '*' -> randomNumberOne * randomNumberTwo;
      default -> null;
    };
  }

  /**
   * Cryptographie de la réponse au captcha et sauvegarde en base
   * @param cryptographyType CryptographyType - Type de cryptographie a utiliser
   * @return BaseCapatcha
   */
  public BaseCapatcha cryptographyAndSaveResponse(CryptographyType cryptographyType) {
    TokenEntity captchaResponseToSave = getCryptographyText(cryptographyType);
    this.tokenRepository.save(captchaResponseToSave);
    return this;
  }

  /**
   * Convertion de la question en image Base 64
   * @return BaseCapatcha
   * @throws IOException
   */
  public BaseCapatcha convertCaptchaQuestionToBase64Image() throws IOException {
    byte[] imageByteFormat = getImageByteFormat();
    captchaQuestionBase64 = Base64.getEncoder().encodeToString(imageByteFormat);
    return this;
  }

  /**
   * Génération des données a sauvegarder
   * @param cryptographyType CryptographyType
   * @return TokenEntity
   */
  private TokenEntity getCryptographyText(CryptographyType cryptographyType) {
    TokenEntity tokenEntity = new TokenEntity();

    // Clé iv pour chiffrement
    byte[] iv = cryptoService.generateRandomByte();
    String ivStringFormat = Base64.getEncoder().encodeToString(iv);

    String cryptoText = switch (cryptographyType) {
      case HASH -> cryptoService.hashText(captchaResponse);
      case ENCRYPT -> cryptoService.encrypt(captchaResponse, iv);
      default -> null;
    };

    if(cryptoText == null)
      return null;

    tokenEntity.setIvKey(cryptographyType.equals(CryptographyType.ENCRYPT) ? ivStringFormat : null);
    tokenEntity.setCryptographyText(cryptoText);
    tokenEntity.setCryptographyType(cryptographyType.toString());
    return tokenEntity;
  }

  /**
   * Création d'une image à partir d'un text
   * @param height int
   * @param width int
   * @return BufferedImage
   */
  private BufferedImage createImageFromText(int height, int width) {

    // Create a BufferedImage object
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    // Get the Graphics2D object
    Graphics2D g2d = image.createGraphics();

    // Set rendering hints for better text rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // Set the background color and clear the image
    g2d.setColor(Color.lightGray);
    g2d.fillRect(0, 0, width, height);

    // Set the font and color for the text
    g2d.setFont(new Font("Serif", Font.BOLD, 20));
    g2d.setColor(Color.BLACK);

    // Get the font metrics for calculating the position
    FontMetrics fm = g2d.getFontMetrics();
    int x = (width - fm.stringWidth(captchaQuestion)) / 2;
    int y = ((height - fm.getHeight()) / 2) + fm.getAscent();

    // Draw the text on the image
    g2d.drawString(captchaQuestion, x, y);

    // Dispose of the Graphics2D object
    g2d.dispose();

    return image;
  }

  /**
   * Creation d'une image à partir d'un text
   * @return byte[]
   * @throws IOException
   */
  private byte[] getImageByteFormat()  throws IOException {
    BufferedImage image = createImageFromText(IMAGE_HEIGHT, IMAGE_WIDTH);
    String imageExtension = APP_IMAGE_TYPE.getImageProperties().getExtension();
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(image, imageExtension, baos);
      byte[] imageBytes = baos.toByteArray();
      return imageBytes;
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public String getCaptchaTitle() {
    return captchaTitle;
  }

  public String getCaptchaQuestion() {
    return captchaQuestion;
  }

  public String getCaptchaResponse() {
    return captchaResponse;
  }

  public String getCaptchaQuestionBase64() {
    return captchaQuestionBase64;
  }

  public String getImagePath() {
    return imagePath;
  }
}
