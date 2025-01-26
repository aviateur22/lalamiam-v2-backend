//package com.ctoutweb.lalamiam.core.useCase.impl;
//
//import com.ctoutweb.lalamiam.core.annotation.CoreService;
//import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
//import com.ctoutweb.lalamiam.core.entity.validateUserCaptchaResponse.impl.BoundaryInputImpl;
//import com.ctoutweb.lalamiam.core.entity.validateUserCaptchaResponse.impl.BoundaryOutputImpl;
//import com.ctoutweb.lalamiam.core.exception.ApplicationException;
//import com.ctoutweb.lalamiam.core.exception.BadRequestException;
//import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
//import com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryOutputAdapter;
//import com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryInputAdapter;
//import com.ctoutweb.lalamiam.core.entity.common.ITokenToValidate;
//import com.ctoutweb.lalamiam.core.entity.common.impl.TokenToValidateImpl;
//import com.ctoutweb.lalamiam.core.provider.IMessageService;
//import com.ctoutweb.lalamiam.core.provider.INotificationService;
//import com.ctoutweb.lalamiam.core.useCase.InputBase;
//import com.ctoutweb.lalamiam.core.useCase.OutputBase;
//import com.ctoutweb.lalamiam.core.useCase.UseCase;
//
//@CoreService
//public class ValidateCaptchaResponseUseCase implements UseCase<ValidateCaptchaResponseUseCase.Input, ValidateCaptchaResponseUseCase.Output> {
//  private final IMessageService messageService;
//  private final ICryptographicService cryptographicService;
//  private final INotificationService notificationService;
//
//  public ValidateCaptchaResponseUseCase(
//          IMessageService messageService,
//          ICryptographicService cryptographicService,
//          INotificationService notificationService
//  ) {
//    this.messageService = messageService;
//    this.cryptographicService = cryptographicService;
//    this.notificationService = notificationService;
//  }
//
//  @Override
//  public Output execute(Input input) {
//    // Récupération de l'entity ValidateUserCaptchaResponse
//    BoundaryInputImpl inputBoundary = input.getBoundaryInput();
//
//
//    // Récupération du cpatchaToken
//    String captchaToken = inputBoundary.getCaptchaToken();
//    String captchaTokenSeparator = inputBoundary.getCaptchaTokenSeparator();
//
//    if((captchaToken == null || captchaToken.isBlank()) || (captchaTokenSeparator == null || captchaTokenSeparator.isBlank())) {
//      messageService.logError("captchaConfiguration error");
//      throw new ApplicationException(messageService.getMessage("captcha.data.error"));
//    }
//
//    String hashOrDecryptCaptchaResponse = inputBoundary.hashOrDecrypteCaptchaResponse();
//    CryptographicType cryptographicType = inputBoundary.cryptographicType();
//
//    if(hashOrDecryptCaptchaResponse == null) {
//      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));
//    }
//
//    // Assemble la réponse client avec le token du captcha
//    String captchaUserResponse = inputBoundary.getCaptchaResponseByUser();
//    String clientResponseWithCaptchaConf = AddCaptchaConfToClientResponse(captchaUserResponse, captchaToken, captchaTokenSeparator);
//
//    // Vérification que la réponse est valide
//      boolean isClientResponseValid = this.isResponseClientValid(
//              clientResponseWithCaptchaConf,
//              hashOrDecryptCaptchaResponse,
//              cryptographicType
//      );
//
//    if(!isClientResponseValid) {
//      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));
//    }
//    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryOutputImpl(isClientResponseValid);
//
//    return Output.getUseCaseOutput(boundaryOutput);
//  }
//
////  /**
////   * Token permettant de valider les réponses ds client
////   * Ce token provient de l'infra
////   * @return String
////   */
////  public String getCaptchaToken() {
////    return this.captchaConfiguration.getCaptchaToken();
////  }
//
//  /**
//   * Ajout du Token et séparator à la réponse du client
//   * @param captchaResponseByUser
//   * @return String
//   */
//  public String AddCaptchaConfToClientResponse(String captchaResponseByUser, String captchaToken, String captchaSeparator) {
//    return captchaToken+ captchaSeparator + captchaResponseByUser;
//  }
//
//  /**
//   * Traitement de la réponse client
//   * @param clientResponseWithCaptchaConf String - Réponse client avec la configuration captcha
//   * @param cryptographicType CryptographicType - Type d'opération de cryptographie
//   * @return boolean
//   */
//  public boolean isResponseClientValid(String clientResponseWithCaptchaConf, String captchaResponse, CryptographicType cryptographicType) {
//    return switch (cryptographicType) {
//      case HASH -> areHashCaptchaResponseAndClientResponseValid(clientResponseWithCaptchaConf, captchaResponse);
//      case ENCRYPTION -> areDecryptCaptchaResponseAndClientResponseValid(clientResponseWithCaptchaConf, captchaResponse);
//    };
//  }
//
//  /**
//   * Validation de la réponse client à partir d'une réponse captcha hashée
//   * @param clientResponseWithCaptchaConf String - Réponse client avec la configuration captcha
//   * @param captchaHashResponse String - Hash de la réponse
//   * @return booelan
//   */
//  public boolean areHashCaptchaResponseAndClientResponseValid(String clientResponseWithCaptchaConf, String captchaHashResponse) {
//
//    ITokenToValidate tokenToValidate = TokenToValidateImpl.getTokenToValidateImpl(
//            clientResponseWithCaptchaConf,
//            captchaHashResponse
//    );
//
//    return cryptographicService.isHashValid(tokenToValidate);
//  }
//
//  /**
//   * Validation de réponse client à partir d'une réponse captcha chiffrée
//   * True si la réponse du captcha se trouve dans la réponse client
//   * @param clientResponseWithCaptchaConf String - Réponse client avec la configuration captcha
//   * @param decryptHashResponse String - Déchiffrement de la réponse captcha
//   * @return boolean
//   */
//  public boolean areDecryptCaptchaResponseAndClientResponseValid(String clientResponseWithCaptchaConf, String decryptHashResponse) {
//    return clientResponseWithCaptchaConf.contains(decryptHashResponse);
//  }
//
//  /**
//   * Input
//   */
//  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl> implements UseCase.Input {
//
//    public static Input getUseCaseInput(IBoundaryInputAdapter boundaryInputAdapter) {
//      return new Input(boundaryInputAdapter);
//    }
//
//    public Input(IBoundaryInputAdapter inputBoundaryAdapter) {
//      super(inputBoundaryAdapter);
//    }
//
//    @Override
//    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter inputBoundaryAdapter) {
//      return BoundaryInputImpl.getBoundaryInputImpl(
//              inputBoundaryAdapter.getCaptchaResponseByUser(),
//              inputBoundaryAdapter.getHashOrDecrypteCaptchaResponse(),
//              inputBoundaryAdapter.getCryptographicType(),
//              inputBoundaryAdapter.getCaptchaToken(),
//              inputBoundaryAdapter.getCaptchaTokenSeparator()
//              );
//    }
//  }
//
//  /**
//   * Output
//   */
//  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {
//
//    public static Output getUseCaseOutput(BoundaryOutputImpl boundaryOutput) {
//      return new Output(boundaryOutput);
//    }
//
//    public Output(BoundaryOutputImpl outputBoundary) {
//      super(outputBoundary);
//    }
//  }
//}
