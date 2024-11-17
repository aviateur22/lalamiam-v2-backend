package usecase.randomCaptchaSelection;

import entity.captcha.strategy.ICaptchaStrategy;
import entity.captcha.CaptchaType;
import entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import entity.captcha.strategy.impl.strategy.CaptchaImageStrategyImpl;
import entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import provider.ICaptchaConfiguration;
import provider.ICaptchaRepository;
import provider.ICryptographicService;
import provider.IMessageService;
import useCase.impl.ClientInscriptionUseCase;
import useCase.impl.RandomCaptchaSelectionUseCase;

public class RandomCaptchaSelectionUseCaseTest {
  RandomCaptchaSelectionUseCase randomCaptchaSelectionUseCase;
  @Mock
  IMessageService messageService;
  @Mock
  ICaptchaRepository captchaRepository;
  @Mock
  ICryptographicService cryptographicService;

  @Mock
  ICaptchaConfiguration captchaConfiguration;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    randomCaptchaSelectionUseCase = new RandomCaptchaSelectionUseCase(cryptographicService, captchaRepository, messageService, captchaConfiguration);
  }

  @Test
  public void randomCaptchaSelectionUseCase_should_return_captcha_strategy() {
    /**
     * given
     */

    RandomCaptchaSelectionUseCase.Input input = new ClientInscriptionUseCase.Input(null);

    /**
     * when
     */
    RandomCaptchaSelectionUseCase.Output output = randomCaptchaSelectionUseCase.execute(input);
    ICaptchaStrategy actualCaptchaStrategy = output.getOutputBoundaryAdapter().getCaptchaContext().getCaptchaStrategy();

    /**
     * then
     */
    Assertions.assertTrue(
            actualCaptchaStrategy.getClass() == CaptchaTextStrategyImpl.class
            || actualCaptchaStrategy.getClass() == CaptchaCalculStrategyImpl.class
      || actualCaptchaStrategy.getClass() == CaptchaImageStrategyImpl.class);
  }

  @Test
  public void randomSelection_should_return_selection_between_0_and_3() {
    /**
     * given
     */
    int numberOfCaptchaTypes = 3;

    /**
     * when
     */
    int randomSelection = randomCaptchaSelectionUseCase.randomSelection(numberOfCaptchaTypes);

    /**
     * then
     */
    Assertions.assertTrue(randomSelection >= 0);
    Assertions.assertTrue(randomSelection <= numberOfCaptchaTypes );

  }

  @Test
  public void selectCaptchaType_should_return_captcha_with_type_image() {
    /**
     * given
     */
    int randomSelection = 0;

    /**
     * when
     */
    CaptchaType actualCaptchaType = randomCaptchaSelectionUseCase.selectCaptchaType(randomSelection);
    CaptchaType expectedCaptchaType = CaptchaType.IMAGE;

    /**
     * then
     */
    Assertions.assertTrue(expectedCaptchaType == actualCaptchaType);
  }

  @Test
  public void selectCaptchaType_should_return_captcha_with_type_text() {
    /**
     * given
     */
    int randomSelection = 1;

    /**
     * when
     */
    CaptchaType actualCaptchaType = randomCaptchaSelectionUseCase.selectCaptchaType(randomSelection);
    CaptchaType expectedCaptchaType = CaptchaType.TEXT;

    /**
     * then
     */
    Assertions.assertTrue(expectedCaptchaType == actualCaptchaType);
  }

  @Test
  public void selectCaptchaType_should_return_captcha_with_type_calcul() {
    /**
     * given
     */
    int randomSelection = 2;

    /**
     * when
     */
    CaptchaType actualCaptchaType = randomCaptchaSelectionUseCase.selectCaptchaType(randomSelection);
    CaptchaType expectedCaptchaType = CaptchaType.CALCUL;

    /**
     * then
     */
    Assertions.assertTrue(expectedCaptchaType == actualCaptchaType);
  }

  @Test void setCaptchaStrategy_should_set_captcha_calcul_strategy() {
    /**
     * given
     */
    CaptchaType captchaCalculType = CaptchaType.CALCUL;

    /**
     * when
     */
    randomCaptchaSelectionUseCase.setCaptchaStrategy(captchaCalculType);

    /**
     * then
     */
  }

}
