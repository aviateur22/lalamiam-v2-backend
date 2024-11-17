package util;

import entity.image.ImageType;
import exception.ApplicationException;
import provider.IMessageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ImageUtil {

  private final IMessageService messageService;
  public ImageUtil(IMessageService messageService) {
    this.messageService = messageService;
  }

  /**
   * Convertion d'une BufferedImage en base64
   * @param bufferedImage BufferedImage
   * @param  imageType ImageType
   * @return String
   * @throws ApplicationException
   */
  public String bufferedImageToBase64(
          BufferedImage bufferedImage,
          ImageType imageType
  ) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      ImageIO.write(bufferedImage, imageType.getMimeType(), baos);
      byte[] imageBytes = baos.toByteArray();
      return Base64.getEncoder().encodeToString(imageBytes);
    } catch (Exception exception) {
      messageService.logError(exception.getMessage());
      throw new ApplicationException(messageService.getMessage("captcha.image.error"));
    }
  }
}
