//package com.ctoutweb.lalamiam.core.useCase.impl;
//
//import com.ctoutweb.lalamiam.core.entity.executeCaptachaStrategy.impl.boundary.BoundaryInputImpl;
//import com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryOutputAdapter;
//import com.ctoutweb.lalamiam.core.adapter.executeCaptchaStrategy.IBoundariesAdapter.IBoundaryInputAdapter;
//import com.ctoutweb.lalamiam.core.entity.captcha.ICaptcha.IGeneratedCaptcha;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaContext;
//import com.ctoutweb.lalamiam.core.entity.captcha.strategy.ICaptchaStrategy;
//import com.ctoutweb.lalamiam.core.entity.executeCaptachaStrategy.impl.boundary.BoundaryOutputImpl;
//import com.ctoutweb.lalamiam.core.provider.IMessageService;
//import com.ctoutweb.lalamiam.core.useCase.InputBase;
//import com.ctoutweb.lalamiam.core.useCase.OutputBase;
//import com.ctoutweb.lalamiam.core.useCase.UseCase;
//
//public class ExecuteCaptchaStrategyUseCase implements UseCase<ExecuteCaptchaStrategyUseCase.Input, ExecuteCaptchaStrategyUseCase.Output> {
//  private final CreateCaptchaImageUseCase createCaptchaImageUseCase;
//  private final IMessageService messageService;
//  private final ICaptchaStrategy defaultStrategyOnError;
//
//  public ExecuteCaptchaStrategyUseCase(
//          CreateCaptchaImageUseCase createCaptchaImageUseCase,
//          IMessageService messageService,
//          ICaptchaStrategy defaultStrategyOnError) {
//    this.createCaptchaImageUseCase = createCaptchaImageUseCase;
//    this.messageService = messageService;
//    this.defaultStrategyOnError = defaultStrategyOnError;
//  }
//
//  @Override
//  public Output execute(Input input) {
//    // Récupération du Context
//    BoundaryInputImpl boundaryInput = input.getBoundaryInput();
//
//    ICaptchaContext captchaContext = boundaryInput.getCaptchaContext();
//
//    IGeneratedCaptcha generatedCaptcha = this.generateCaptcha(captchaContext);
//
//    // Si Erreur de génération alors execute le capctcha de Type calcul
//    if(generatedCaptcha == null){
//      messageService.logError("Erreur généraration captcha - Utilisation CaptchaCalculImpl");
//      generatedCaptcha = defaultStrategyOnError.generateCaptcha();
//
//    }
//
//    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryOutputImpl(generatedCaptcha);
//
//    return Output.getUseCaseOutput(boundaryOutput);
//  }
//
//  /**
//   * Génération du captcha
//   * @param captchaContext CaptchaContext
//   * @return CaptchaGeneratedInformation
//   */
//  public IGeneratedCaptcha generateCaptcha(ICaptchaContext captchaContext) {
//    return captchaContext.execute();
//  }
//
//
//  /**
//   * USeCase Input
//   */
//  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl>  implements UseCase.Input {
//
//    public static Input getUseCaseInput(IBoundaryInputAdapter inputBoundaryAdapter) {
//      return new Input(inputBoundaryAdapter);
//    }
//
//    public Input(IBoundaryInputAdapter inputBoundaryAdapter) {
//      super(inputBoundaryAdapter);
//    }
//    @Override
//    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter boundaryInputAdapter) {
//      return BoundaryInputImpl.getBoundaryInputImpl(inputBoundaryAdapter.getCaptchaContext());
//    }
//  }
//
//  /**
//   * USeCase Output
//   */
//  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {
//
//    public Output(BoundaryOutputImpl boundaryOutput) {
//      super(boundaryOutput);
//    }
//
//    public static Output getUseCaseOutput(BoundaryOutputImpl boundaryOutput) {
//      return new Output(boundaryOutput);
//    }
//  }
//}
