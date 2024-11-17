package useCase.impl;

import adapter.randomCaptchaSelection.IBoundaryOutputAdapter;
import entity.captcha.strategy.ICaptchaStrategyFactory;
import entity.captcha.strategy.impl.context.CaptchaContextImpl;
import entity.captcha.strategy.ICaptchaStrategy;
import entity.captcha.CaptchaType;
import entity.randomCaptchaSelection.impl.BoundaryOutputImpl;
import entity.captcha.strategy.ICaptchaContext;
import provider.ICaptchaConfiguration;
import provider.ICaptchaRepository;
import provider.ICryptographicService;
import provider.IMessageService;
import useCase.OutputBase;
import useCase.UseCase;
import util.IntegerUtil;

import static factory.CaptchaStrategyFactory.getCaptchaStrategyFactory;

public class RandomCaptchaSelectionUseCase implements UseCase<RandomCaptchaSelectionUseCase.Input, RandomCaptchaSelectionUseCase.Output> {
  private final ICaptchaContext captchaContext = new CaptchaContextImpl();
  private final ICaptchaStrategyFactory captchaStrategyFactory;
  private final ICryptographicService cryptographicService;
  private final ICaptchaRepository captchaRepository;
  private final IMessageService messageService;
  private final ICaptchaConfiguration captchaConfiguration;
  public RandomCaptchaSelectionUseCase(
          ICryptographicService cryptographicService,
          ICaptchaRepository captchaRepository,
          IMessageService messageService,
          ICaptchaConfiguration captchaConfiguration) {
    this.cryptographicService = cryptographicService;
    this.captchaRepository = captchaRepository;
    this.messageService = messageService;
    this.captchaConfiguration = captchaConfiguration;
    this.captchaStrategyFactory = getCaptchaStrategyFactory(
            messageService,
            cryptographicService,
            captchaRepository,
            captchaConfiguration
    );
  }


  @Override
  public Output execute(Input input) {
    int randomSelection = this.randomSelection(CaptchaType.values().length - 1);

    // Selection d'un type de captcha en mode Random
    CaptchaType captchaSelection = selectCaptchaType(randomSelection);

    // Enregistrement de la strategie du captcha
    setCaptchaStrategy(captchaSelection);

    // Boundary
    BoundaryOutputImpl outputBoundary = BoundaryOutputImpl.getBoundaryOutputImpl(captchaContext);

    return Output.getUseCaseOutput(outputBoundary);
  }

  /**
   * Selection d'un chiffre compris entre 0 et le nombre de captchaTypes
   * @param numberOfCaptchaTypes int - Nombre de captcha disponible
   * @return int
   */
  public int randomSelection(int numberOfCaptchaTypes) {
    return IntegerUtil.generateNumberBetween(0, numberOfCaptchaTypes);
  }

  /**
   * Renvoie la selection du captcha
   * @param randomSelection int - Chiffre random pour la selection du cpatcha
   * @return CaptchaType
   */
  public CaptchaType selectCaptchaType(int randomSelection) {
    CaptchaType[] types = CaptchaType.values();
    return types[randomSelection];
  }

  /**
   * Mise à jour de la stratégie captcha a executer
   * @param randomCaptchaType CaptchaType
   */
  public void setCaptchaStrategy(CaptchaType randomCaptchaType) {
    ICaptchaStrategy captchaStrategy = captchaStrategyFactory.getCaptchaStrategy(randomCaptchaType);
    captchaContext.setCaptchaStrategy(captchaStrategy);
  }

  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {

    public static Output getUseCaseOutput(BoundaryOutputImpl boundaryOutput) {
      return new Output(boundaryOutput);
    }

    public Output(BoundaryOutputImpl boundaryOutput) {
      super(boundaryOutput);
    }
  }
}
