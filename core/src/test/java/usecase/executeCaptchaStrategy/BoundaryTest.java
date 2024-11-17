package usecase.executeCaptchaStrategy;

import common.DataForTest;
import entity.captcha.strategy.impl.strategy.CaptchaCalculStrategyImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import provider.ICryptographicService;
import provider.IMessageService;
import useCase.impl.ExecuteCaptchaStrategyUseCase;

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
    adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter inputBoundaryAdapter = DataForTest.getExecuteCaptchaStrategyInputBoundaryAdapter(messageService, cryptographicService);

    /**
     * when
     */
    ExecuteCaptchaStrategyUseCase.Input input = ExecuteCaptchaStrategyUseCase.Input.getUseCaseInput(inputBoundaryAdapter);
    adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter actualInputBoundary = input.getBoundaryInput();

    /**
     *
     */
    Assertions.assertNotNull(actualInputBoundary.getCaptchaContext());
    Assertions.assertEquals(CaptchaCalculStrategyImpl.class,  actualInputBoundary.getCaptchaContext().getCaptchaStrategy().getClass());

  }
}
