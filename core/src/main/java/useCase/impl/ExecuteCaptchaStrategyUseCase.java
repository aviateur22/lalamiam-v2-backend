package useCase.impl;

import adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryOutputAdapter;
import adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter;
import entity.captcha.ICaptcha.IGeneratedCaptcha;
import entity.captcha.strategy.ICaptchaContext;
import entity.captcha.strategy.ICaptchaStrategy;
import entity.executeCaptachaStrategy.impl.boundary.BoundaryInputImpl;
import entity.executeCaptachaStrategy.impl.boundary.BoundaryOutputImpl;
import provider.IMessageService;
import useCase.InputBase;
import useCase.OutputBase;
import useCase.UseCase;

public class ExecuteCaptchaStrategyUseCase implements UseCase<ExecuteCaptchaStrategyUseCase.Input, ExecuteCaptchaStrategyUseCase.Output> {
  private final CreateCaptchaImageUseCase createCaptchaImageUseCase;
  private final IMessageService messageService;
  private final ICaptchaStrategy defaultStrategyOnError;

  public ExecuteCaptchaStrategyUseCase(
          CreateCaptchaImageUseCase createCaptchaImageUseCase,
          IMessageService messageService,
          ICaptchaStrategy defaultStrategyOnError) {
    this.createCaptchaImageUseCase = createCaptchaImageUseCase;
    this.messageService = messageService;
    this.defaultStrategyOnError = defaultStrategyOnError;
  }

  @Override
  public Output execute(Input input) {
    // Récupération du Context
    BoundaryInputImpl boundaryInput = input.getBoundaryInput();

    ICaptchaContext captchaContext = boundaryInput.getCaptchaContext();

    IGeneratedCaptcha generatedCaptcha = this.generateCaptcha(captchaContext);

    // Si Erreur de génération alors execute le capctcha de Type calcul
    if(generatedCaptcha == null){
      messageService.logError("Erreur généraration captcha - Utilisation CaptchaCalculImpl");
      generatedCaptcha = defaultStrategyOnError.generateCaptcha();

    }

    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryOutputImpl(generatedCaptcha);

    return Output.getUseCaseOutput(boundaryOutput);
  }

  /**
   * Génération du captcha
   * @param captchaContext CaptchaContext
   * @return CaptchaGeneratedInformation
   */
  public IGeneratedCaptcha generateCaptcha(ICaptchaContext captchaContext) {
    return captchaContext.execute();
  }


  /**
   * USeCase Input
   */
  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl>  implements UseCase.Input {

    public static Input getUseCaseInput(IBoundaryInputAdapter inputBoundaryAdapter) {
      return new Input(inputBoundaryAdapter);
    }

    public Input(IBoundaryInputAdapter inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }
    @Override
    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter boundaryInputAdapter) {
      return BoundaryInputImpl.getBoundaryInputImpl(inputBoundaryAdapter.getCaptchaContext());
    }
  }

  /**
   * USeCase Output
   */
  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {

    public Output(BoundaryOutputImpl boundaryOutput) {
      super(boundaryOutput);
    }

    public static Output getUseCaseOutput(BoundaryOutputImpl boundaryOutput) {
      return new Output(boundaryOutput);
    }
  }
}
