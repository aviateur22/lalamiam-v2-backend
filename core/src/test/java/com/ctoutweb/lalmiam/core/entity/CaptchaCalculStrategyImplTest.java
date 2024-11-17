package com.ctoutweb.lalmiam.core.entity;

import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.provider.ICaptchaConfiguration;
import com.ctoutweb.lalamiam.core.provider.ICaptchaRepository;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;

public class CaptchaCalculStrategyImplTest {
  @Mock
  IMessageService messageService;
  @Mock
  ICaptchaRepository captchaRepository;
  @Mock
  ICryptographicService cryptographicService;

  @Mock
  ICaptchaConfiguration captchaConfiguration;

  CaptchaCalculStrategyImpl captchaCalculStrategy;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    captchaCalculStrategy = new CaptchaCalculStrategyImpl(messageService, cryptographicService, captchaRepository, captchaConfiguration);
  }

  @Test
  public void generateRandomNumber_should_return_random_number_between_0and_9() {

    for(int i = 0; i < 10; i++) {
      /**
       * when
       */
      int randomNumber = captchaCalculStrategy.generateRandomNumberInBetween(0, 9);

      /**
       * then
       */
      Assertions.assertTrue(randomNumber >= 0);
      Assertions.assertTrue(randomNumber <= 9);

    }
  }

  @Test
  public void generateRandomMathOperation_should_return_1_random_operation() {

    for(int i = 0; i < 10; i++) {
      /**
       * when
       */
      char randomMathOperation = captchaCalculStrategy.generateRandomMathOperation();

      /**
       * then
       */
      Assertions.assertTrue(randomMathOperation == '+' || randomMathOperation == '-' || randomMathOperation == '*');

    }
  }

  @Test
  public void generateCaptchaResponse_should_be_equal_to_8() {

    /**
     * given
     */
    char randomMathOperation = '+';
    int randomNumber1 = 0;
    int randomNumber2 = 8;
    String messageException = "oups";

    /**
     * when
     */
    int actualCaptchaResponse = captchaCalculStrategy.calculateResult(randomNumber1, randomMathOperation, randomNumber2, messageException);
    int expectedCaptchaResponse = 8;

    /**
     * then
     */
    Assertions.assertEquals(expectedCaptchaResponse, actualCaptchaResponse);

  }

  @Test
  public void generateCaptchaResponse_should_be_equal_to_minus_5() {

    /**
     * given
     */
    char randomMathOperation = '-';
    int randomNumber1 = 3;
    int randomNumber2 = 8;
    String messageException = "oups";

    /**
     * when
     */
    int actualCaptchaResponse = captchaCalculStrategy.calculateResult(randomNumber1, randomMathOperation, randomNumber2, messageException);
    int expectedCaptchaResponse = -5;

    /**
     * then
     */
    Assertions.assertEquals(expectedCaptchaResponse, actualCaptchaResponse);

  }

  @Test
  public void generateCaptchaResponse_should_be_equal_to_25() {

    /**
     * given
     */
    char randomMathOperation = '*';
    int randomNumber1 = 5;
    int randomNumber2 = 5;
    String messageException = "oups";

    /**
     * when
     */
    int actualCaptchaResponse = captchaCalculStrategy.calculateResult(randomNumber1, randomMathOperation, randomNumber2, messageException);
    int expectedCaptchaResponse = 25;

    /**
     * then
     */
    Assertions.assertEquals(expectedCaptchaResponse, actualCaptchaResponse);

  }

  @Test
  public void generateCaptchaQuestion_should_return_00_plus_08() {

    /**
     * given
     */
    char randomMathOperation = '+';
    int randomNumber1 = 0;
    int randomNumber2 = 8;

    /**
     * when
     */
    String actualCaptchaQuestion = captchaCalculStrategy.generateCaptchaQuestion(randomNumber1, randomMathOperation, randomNumber2);
    String expectedCaptchaQuestion = "00 + 08";

    /**
     * then
     */
    Assertions.assertEquals(expectedCaptchaQuestion, actualCaptchaQuestion);
  }

  @Test
  public void generateCaptchaQuestion_should_return_05_multi_02() {

    /**
     * given
     */
    char randomMathOperation = '*';
    int randomNumber1 = 5;
    int randomNumber2 = 2;

    /**
     * when
     */
    String actualCaptchaQuestion = captchaCalculStrategy.generateCaptchaQuestion(randomNumber1, randomMathOperation, randomNumber2);
    String expectedCaptchaQuestion = "05 * 02";

    /**
     * then
     */
    Assertions.assertEquals(expectedCaptchaQuestion, actualCaptchaQuestion);
  }

  @Test
  public void generateCaptcha_should_return_random_result() {

    /**
     * given
     */
    Mockito.when( messageService.getMessage("captcha.calcul.title")).thenReturn("Titre capctha");
    Mockito.when(cryptographicService.cryptographyTextAndSave(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(DataForTest.fakeCryptographyResultWhenHash());


    /**
     * when
     */
    IGeneratedCaptcha actualResponse = captchaCalculStrategy.generateCaptcha();

    /**
     * then
     */
    Assertions.assertNotNull(actualResponse.getCaptchaQuestion());
    Assertions.assertNotNull(actualResponse.getCaptchaTitle());
    Assertions.assertNotNull(actualResponse.getResponseId());
  }
}
