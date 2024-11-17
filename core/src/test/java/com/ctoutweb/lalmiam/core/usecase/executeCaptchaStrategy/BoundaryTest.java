package com.ctoutweb.lalmiam.core.usecase.executeCaptchaStrategy;

import com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.impl.ExecuteCaptchaStrategyUseCase;

public class BoundaryTest {
  @Mock
  ICryptographicService cryptographicService;

  @Mock
  IMessageService messageService;

  @Test
  public void should_validate_input() {
    /**
     * given
     */
    IBoundariesAdapter.IBoundaryInputAdapter inputBoundaryAdapter = DataForTest.getExecuteCaptchaStrategyInputBoundaryAdapter(messageService, cryptographicService);

    /**
     * when
     */
    ExecuteCaptchaStrategyUseCase.Input input = ExecuteCaptchaStrategyUseCase.Input.getUseCaseInput(inputBoundaryAdapter);
    IBoundariesAdapter.IBoundaryInputAdapter actualInputBoundary = input.getBoundaryInput();

    /**
     *
     */
    Assertions.assertNotNull(actualInputBoundary.getCaptchaContext());
    Assertions.assertEquals(CaptchaCalculStrategyImpl.class,  actualInputBoundary.getCaptchaContext().getCaptchaStrategy().getClass());

  }
}
