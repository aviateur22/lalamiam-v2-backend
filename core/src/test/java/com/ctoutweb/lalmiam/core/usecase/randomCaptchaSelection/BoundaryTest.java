package com.ctoutweb.lalmiam.core.usecase.randomCaptchaSelection;

import com.ctoutweb.lalamiam.core.adapter.randomCaptchaSelection.IBoundaryOutputAdapter;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.context.CaptchaContextImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaTextStrategyImpl;
import com.ctoutweb.lalamiam.core.entity.randomCaptchaSelection.impl.BoundaryOutputImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.impl.RandomCaptchaSelectionUseCase;

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
