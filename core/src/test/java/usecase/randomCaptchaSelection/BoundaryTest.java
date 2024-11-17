package usecase.randomCaptchaSelection;

import adapter.randomCaptchaSelection.IBoundaryOutputAdapter;
import common.DataForTest;
import entity.captcha.strategy.impl.context.CaptchaContextImpl;
import entity.captcha.strategy.ICaptchaStrategy;
import entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import entity.randomCaptchaSelection.impl.BoundaryOutputImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import provider.ICryptographicService;
import provider.IMessageService;
import useCase.impl.RandomCaptchaSelectionUseCase;

public class BoundaryTest {
  @Mock
  ICryptographicService cryptographicService;

  @Mock
  IMessageService messageService;

  @Test
  public void should_validate_Output_with_CaptchaTextStrategyImpl() {
    /**
     * given
     */
    CaptchaContextImpl captchaContextCalculStrategy = DataForTest.fakeCaptchaContextTextStrategy(messageService, cryptographicService);
    BoundaryOutputImpl outputBoundary = new BoundaryOutputImpl(captchaContextCalculStrategy);

    /**
     * when
     */
    RandomCaptchaSelectionUseCase.Output actualOutput =  new RandomCaptchaSelectionUseCase.Output(outputBoundary);
    IBoundaryOutputAdapter outputBoundaryAdapter = actualOutput.getOutputBoundaryAdapter();
    ICaptchaStrategy actualCaptchaStrategy = outputBoundaryAdapter.getCaptchaContext().getCaptchaStrategy();

    /**
     * then
     */
    Assertions.assertEquals(actualCaptchaStrategy.getClass(), CaptchaTextStrategyImpl.class);
  }

  @Test
  public void should_validate_Output_with_CaptchaCalculStrategyImpl() {
    /**
     * given
     */
    CaptchaContextImpl captchaContextCalculStrategy = DataForTest.fakeCaptchaContextCalculStrategy(messageService, cryptographicService);
    BoundaryOutputImpl outputBoundary = new BoundaryOutputImpl(captchaContextCalculStrategy);

    /**
     * when
     */
    RandomCaptchaSelectionUseCase.Output actualOutput = new RandomCaptchaSelectionUseCase.Output(outputBoundary);
    IBoundaryOutputAdapter outputBoundaryAdapter = actualOutput.getOutputBoundaryAdapter();
    ICaptchaStrategy actualCaptchaStrategy = outputBoundaryAdapter.getCaptchaContext().getCaptchaStrategy();

    /**
     * then
     */
    Assertions.assertEquals(actualCaptchaStrategy.getClass(), CaptchaCalculStrategyImpl.class);
  }
}
