package entity;

import common.DataForTest;
import entity.captcha.ICaptcha;
import entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import provider.ICaptchaConfiguration;
import provider.ICaptchaRepository;
import provider.ICryptographicService;
import provider.IMessageService;

import java.util.List;

public class CaptchaImageStrategyImplTest {
  @Mock
  IMessageService messageService;
  @Mock
  ICaptchaRepository captchaRepository;
  @Mock
  ICryptographicService cryptographicService;

  @Mock
  ICaptchaConfiguration captchaConfiguration;
  CaptchaImageStrategyImpl captchaImageStrategy;

  @BeforeEach
  public void init() {
    captchaImageStrategy = new CaptchaImageStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration );
  }

  @Test
  public void getRandomCaptchaImageInformation() {
    /**
     * given
     */
    List<ICaptcha.IAvailableCaptchaImage> availableCaptchaImages = List.of(
            DataForTest.fakeAvailableCaptchaImage("reponse 1", "c:/ccccc-1"),
            DataForTest.fakeAvailableCaptchaImage("reponse 2", "c:/ccccc-2"),
            DataForTest.fakeAvailableCaptchaImage("reponse 3", "c:/ccccc-3"),
            DataForTest.fakeAvailableCaptchaImage("reponse 4", "c:/ccccc-4"),
            DataForTest.fakeAvailableCaptchaImage("reponse 5", "c:/ccccc-5"),
            DataForTest.fakeAvailableCaptchaImage("reponse 6", "c:/ccccc-6")
    );

    /**
     * when
     */
    ICaptcha.IAvailableCaptchaImage availableCaptchaImage = captchaImageStrategy.getRandomCaptchaImageInformation(availableCaptchaImages);

    /**
     * then
     */
    Assertions.assertNotNull(availableCaptchaImage,"AvailableCaptchaImage ne peut pas être NULL");
  }

}
