package com.ctoutweb.lalamiam.infra.model.captcha.impl;

import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaGeneration;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaImage;
import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.utility.NumberUtil;
import com.ctoutweb.lalamiam.infra.utility.TextUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.AttributedString;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.*;

@Component
@PropertySource({"classpath:application.properties"})
public class CaptchaGenerationImpl implements ICaptchaGeneration {
  private static final Logger LOGGER = LogManager.getLogger();
  @Value("${zone.id}")
  String zoneId;

  @Value("${captcha.iv.key}")
  String captchaIvKey;

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
   * Id de la réponse sauvegardé en base de données
   */
  private Long captchaResponseId;

  /**
   * Question captcha au format IImageBase64
   */
  private IImageBase64 captchaQuestionBase64;

  private ZonedDateTime validUntil;

  /**
   * Pour les captcha de type image.
   * Image selectionnée pour le captcha
   */
  private File imageFile;
  private final IMessageService messageService;
  private final ITokenRepository tokenRepository;
  private final ICryptoService cryptoService;
  private final Factory factory;

  public CaptchaGenerationImpl(
          IMessageService messageService,
          ITokenRepository tokenRepository,
          ICryptoService cryptoService,
          Factory factory
  ) {
    this.messageService = messageService;
    this.tokenRepository = tokenRepository;
    this.cryptoService = cryptoService;
    this.factory = factory;
  }
  @Override
  public CaptchaGenerationImpl generate(CaptchaType captchaType) {
    resetValue();
    switch (captchaType) {
      case TEXT -> captchaTypeText();
      default -> captchaTypeCalcul();
    };
    return this;
  }
  @Override
  public ICaptchaGeneration generate(List<ICaptchaImage> captchaImages, List<File> captchaImageFiles) {
    resetValue();
    captchaTypeImage(captchaImages, captchaImageFiles);
    return this;
  }
  @Override
  public ICaptchaGeneration cryptographyAndSaveResponse(CryptographyType cryptographyType) {
    TokenEntity captchaResponseToSave = getCryptographyText(cryptographyType);
    TokenEntity savedCaptcha = tokenRepository.save(captchaResponseToSave);
    captchaResponseId = savedCaptcha.getId();
    return this;
  }
  @Override
  public ICaptchaGeneration convertCaptchaQuestionToBase64Image() throws IOException {

    // MimeType de l'image
    String mimeType = APP_IMAGE_TYPE.getImageProperties().getMimeType().getName();

    // Image au format Byte
    byte[] imageByteFormat = imageFile != null ?
            generateFileImageByteFormat() : generateQuestionImageByteFormat();

    String imageBase64 = Base64.getEncoder().encodeToString(imageByteFormat);

    // Données images
    captchaQuestionBase64 = factory.getImpl(mimeType, imageBase64);

    return this;
  }
  @Override
  public ICaptcha getCaptcha() {
    // Chiffrement de l'id du captcha
    byte[] captchaIvKeyByte = cryptoService.getByteArrayFromBase64(captchaIvKey);

    if(captchaIvKeyByte == null){
      LOGGER.error(()->String.format("Impossible de produire un byte[] à partir de %s", captchaIvKey));
      throw new BadRequestException(messageService.getMessage("captcha.config.error"));
    }

    String encryptCaptchaResponseId = cryptoService.encrypt(String.valueOf(captchaResponseId), captchaIvKeyByte);

    return factory.getImpl(captchaTitle, captchaQuestionBase64, encryptCaptchaResponseId);
  }

  /**
   *
   * @param captchaImages List<ICaptchaImage> - Données des images en base de données
   */
  public void captchaTypeImage(List<ICaptchaImage> captchaImages, List<File> captchaImageFiles) {
    final int MIN = 0;

    captchaTitle = this.messageService.getMessage("captcha.image.title");

    int randomCaptchaIndex = NumberUtil.generateNumberBetween(MIN, captchaImageFiles.size() - 1);
    imageFile = captchaImageFiles.get(randomCaptchaIndex);

    // Recherche des données liées a l'image selectionnée
    ICaptchaImage captchaImageSelected = captchaImages
            .stream()
            .filter(image->image.getName().equals(imageFile.getName()))
            .findFirst()
            .orElse(null);

    captchaResponse = captchaImageSelected.getResponse();
  }

  public void captchaTypeText() {
    final int MIN = 6;
    final int MAX = 11;

    captchaTitle = messageService.getMessage("captcha.text.title");

    captchaQuestion = TextUtility.generateRandomText(NumberUtil.generateNumberBetween(MIN, MAX));

    // La réponse est équivalente à la question
    captchaResponse = captchaQuestion;
  }

  public void captchaTypeCalcul() {
    final int MIN = 0;
    final int MAX = 9;
    final char[] CALCUl_OPERATION = {'+', '-', '*'};

    // Chiffre 1
    final int RANDOM_NUMBER_1 = NumberUtil.generateNumberBetween(MIN, MAX);

    // Calcul chiffre 2
    final int RANDOM_NUMBER_2 = NumberUtil.generateNumberBetween(MIN, MAX);

    // Operation mathématique
    final char RANDOM_MATH_OPERATION =  CALCUl_OPERATION[NumberUtil.generateNumberBetween(0, CALCUl_OPERATION.length - 1)];

    captchaTitle = messageService.getMessage("captcha.calcul.title");

    captchaResponse = generateCalculResponse(RANDOM_NUMBER_1, RANDOM_NUMBER_2, RANDOM_MATH_OPERATION).toString();

    captchaQuestion = String.format("%02d %c %02d", RANDOM_NUMBER_1, RANDOM_MATH_OPERATION, RANDOM_NUMBER_2);
  }

  /**
   * Reset les valeurs des données pour un captcha
   */
  private void resetValue() {
    this.imageFile = null;
    this.captchaResponseId = null;
    this.captchaResponse = null;
    this.captchaQuestion = null;
    this.captchaQuestionBase64 = null;
    this.captchaTitle = null;
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
    tokenEntity.setValidUntil(ZonedDateTime.of(LocalDateTime.now().plusHours(3), ZoneId.of(zoneId)));
    return tokenEntity;
  }

  /**
   * Pour les captcha de type text et calcul
   * Creation d'une image à partir de la question d'un captcha
   * @return byte[]
   * @throws IOException
   */
  private byte[] generateQuestionImageByteFormat()  throws IOException {
    BufferedImage image = createImageFromText(IMAGE_HEIGHT, IMAGE_WIDTH);
    String imageExtension = APP_IMAGE_TYPE.getImageProperties().getExtension();
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(image, imageExtension, baos);
      byte[] imageBytes = baos.toByteArray();
      return imageBytes;
    }
  }

  /**
   * Pour les captcha de type image
   * Creation d'une image à partir d'un fichier
   * @return byte[]
   * @throws IOException
   */
  private byte[] generateFileImageByteFormat()  throws IOException {
    BufferedImage image = ImageIO.read(imageFile);
    String imageExtension = APP_IMAGE_TYPE.getImageProperties().getExtension();
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(image, imageExtension, baos);
      byte[] imageBytes = baos.toByteArray();
      return imageBytes;
    }
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
    Graphics2D g2d = image.createGraphics();

    // Enable anti-aliasing for better rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // Set background color and fill the image
    g2d.setColor(Color.LIGHT_GRAY);
    g2d.fillRect(0, 0, width, height);

    // Set font and color
    Font font = new Font("Serif", Font.BOLD, 20);
    g2d.setFont(font);
    g2d.setColor(Color.BLACK);

    // Get font metrics
    FontMetrics fm = g2d.getFontMetrics();
    int totalWidth = 0;

    // Calculate total width of the string with additional spacing
    for (int i = 0; i < captchaQuestion.length(); i++) {
      totalWidth += fm.charWidth(captchaQuestion.charAt(i));
    }

    // Add custom letter spacing
    int letterSpacing = 5;
    totalWidth += (captchaQuestion.length() - 1) * letterSpacing;

    // Calculate the x position to center the captchaQuestion
    int x = (width - totalWidth) / 2;
    int y = ((height - fm.getHeight()) / 2) + fm.getAscent();


    // Main Text: Draw the actual captchaQuestion in black
    g2d.setColor(Color.BLACK);
    x = (width - totalWidth) / 2;  // Reset x to original position
    for (int i = 0; i < captchaQuestion.length(); i++) {
      g2d.drawString(String.valueOf(captchaQuestion.charAt(i)), x, y);
      x += fm.charWidth(captchaQuestion.charAt(i)) + letterSpacing;
    }

    // Dispose of the Graphics2D object
    g2d.dispose();

    return image;
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public String getCaptchaTitle() {
    return captchaTitle;
  }

  public Long getCaptchaResponseId() {
    return captchaResponseId;
  }

  public IImageBase64 getCaptchaQuestionBase64() {
    return captchaQuestionBase64;
  }

  public ZonedDateTime getValidUntil() {
    return ZonedDateTime.of(LocalDateTime.now().plusHours(3), ZoneId.of(zoneId));
  }

  public String getCaptchaQuestion() {
    return captchaQuestion;
  }
  public String getCaptchaResponse() {
    return captchaResponse;
  }
}
