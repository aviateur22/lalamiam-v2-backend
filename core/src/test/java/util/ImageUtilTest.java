package util;

import common.DataForTest;
import entity.image.ImageType;
import exception.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import provider.IMessageService;

import java.awt.image.BufferedImage;

public class ImageUtilTest {
  @Mock
  IMessageService messageService;

  ImageUtil imageUtil;



  @BeforeEach
  public void init()  {
    MockitoAnnotations.openMocks(this);
    imageUtil = new ImageUtil(messageService);
  }

  @Test
  public void bufferedImageToBase64_should_parse_to_base64() {
    /**
     * given
     */
    BufferedImage bufferedImage = DataForTest.fakeImage();
    ImageType imageType = ImageType.PNG;
    String exceptionCode = "captcha.image.error";
    Mockito.when(messageService.getMessage(exceptionCode)).thenReturn("exception message");
    Mockito.doNothing().when(messageService).logError(Mockito.anyString());

    /**
     * when
     */
    String imageBase64 = imageUtil.bufferedImageToBase64(bufferedImage, imageType);

    /**
     * then
     */
    Assertions.assertNotNull(imageBase64);
  }

  @Test
  public void bufferedImageToBase64_should_throw_ApplicationException_when_unvalid_data() {
    /**
     * given
     */
    ImageType imageType = ImageType.PNG;

    String exceptionCode = "captcha.image.error";
    String expectedErrorMessage = "exception message";
    Mockito.when(messageService.getMessage(exceptionCode)).thenReturn(expectedErrorMessage);
    Mockito.doNothing().when(messageService).logError(Mockito.anyString());

    /**
     * when
     */
    Exception exception = Assertions.assertThrows(ApplicationException.class, ()-> imageUtil.bufferedImageToBase64(null, imageType));

    /**
     * then
     */
    Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
  }
}
