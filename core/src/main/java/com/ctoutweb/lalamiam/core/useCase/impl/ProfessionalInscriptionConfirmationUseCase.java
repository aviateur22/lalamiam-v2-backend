//package com.ctoutweb.lalamiam.core.useCase.impl;
//
//import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
//import com.ctoutweb.lalamiam.core.entity.common.ITokenToValidate;
//import com.ctoutweb.lalamiam.core.entity.common.impl.TokenToValidateImpl;
//import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.IProfessionalInscriptionConfirmation;
//import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.impl.boundary.BoundaryInputImpl;
//import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.impl.boundary.BoundaryOutputImpl;
//import com.ctoutweb.lalamiam.core.exception.BadRequestException;
//import com.ctoutweb.lalamiam.core.provider.IMessageService;
//import com.ctoutweb.lalamiam.core.provider.INotificationService;
//import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionConfirmationRepository;
//import com.ctoutweb.lalamiam.core.provider.IValidateTokenService;
//import com.ctoutweb.lalamiam.core.useCase.InputBase;
//import com.ctoutweb.lalamiam.core.useCase.UseCase;
//import com.ctoutweb.lalamiam.core.adapter.professionalInscriptionConfirmation.IBoundariesAdapter;
//import com.ctoutweb.lalamiam.core.adapter.professionalInscriptionConfirmation.IBoundariesAdapter.IBoundaryInputAdapter;
//import com.ctoutweb.lalamiam.core.useCase.OutputBase;
//
//import java.time.LocalDateTime;
//
///**
// * Confirmation de l'inscription par le professionel
// * Cette action intervient apres la reception d'un email
// * qui contient un lien pour confirmer l'inscription
// */
//public class ProfessionalInscriptionConfirmationUseCase implements UseCase<ProfessionalInscriptionConfirmationUseCase.Input, ProfessionalInscriptionConfirmationUseCase.Output> {
//  private final IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository;
//  private final INotificationService notificationService;
//  private final IMessageService messageService;
//  private final IValidateTokenService validateTokenService;
//
//  public ProfessionalInscriptionConfirmationUseCase(
//          IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository,
//          INotificationService notificationService,
//          IMessageService messageService,
//          IValidateTokenService validateTokenService) {
//    this.professionalInscriptionConfirmationRepository = professionalInscriptionConfirmationRepository;
//    this.notificationService = notificationService;
//    this.messageService = messageService;
//    this.validateTokenService = validateTokenService;
//  }
//
//  @Override
//  public Output execute(Input input) {
//
//    BoundaryInputImpl inputBoundary = input.getBoundaryInput();
//
//    // Recherche des données du professionel
//    IProfessional professional = findProfessionalInformationByEmail(inputBoundary.getProfessionalEmail());
//
//    if(professional == null) {
//      throw new BadRequestException(messageService.getMessage("professional.account.confirmed.error"));
//    }
//
//    // Le compte du professionel ne doit pas être déja confirmé par le professionel
//    boolean isProfessionalAccountActive = isProfessionalAccountConfirmed(professional);
//
//    if(isProfessionalAccountActive) {
//      throw new BadRequestException(messageService.getMessage("professional.account.already.confirmed"));
//    }
//
//// Récupération des tokens de confirmation en base de données
//    IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken professionalConfirmationToken = findProfessionalConfirmationToken(professional.getProfessionalId());
//
//    // Vérification de la date de validation
//    LocalDateTime actualConfirmationDate = LocalDateTime.now();
//    boolean isConfirmationLimitDateValid = isConfirmationLimitTimeValid(professionalConfirmationToken.getActivatedLimitTime(), actualConfirmationDate);
//
//    if(!isConfirmationLimitDateValid) {
//      throw new BadRequestException(messageService.getMessage("professional.account.limit.date.expired"));
//    }
//
//
//    if(professionalConfirmationToken == null) {
//      throw new BadRequestException(messageService.getMessage("professional.account.confirmed.token.error"));
//    }
//
//    // Validation des Tokens
//    ITokenToValidate emailTokenToValidate = TokenToValidateImpl.getTokenToValidateImpl(
//            inputBoundary.getProfessionalEmailToken(),
//            professionalConfirmationToken.getEmailHashToken()
//    );
//
//    ITokenToValidate urlTokenToValidate = TokenToValidateImpl.getTokenToValidateImpl(
//            inputBoundary.getProfessionalUrlToken(),
//            professionalConfirmationToken.getUrlHashToken()
//    );
//
//    boolean areProfessionalTokenValid = areProfessionalTokenValid(emailTokenToValidate, urlTokenToValidate);
//
//    if(!areProfessionalTokenValid){
//      throw new BadRequestException(messageService.getMessage("professional.confirmation.account.token.error"));
//    }
//
//    // Validation de l'inscription
//    professionalInscriptionConfirmationRepository.confirmProfessionalRegister(professional.getProfessionalId());
//
//    final boolean IS_INSCRIPTION_CONFIRM = true;
//
//    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryInputImpl(
//            IS_INSCRIPTION_CONFIRM,
//            messageService.getMessage("register.success")
//    );
//
//    // Renvoie UseCaseOutput
//    return Output.getUseCaseOutput(boundaryOutput);
//  }
//
//  /**
//   * Recherche d'un professionel par son email
//   * @param professionalEmail String
//   * @return Professional
//   */
//  public IProfessional findProfessionalInformationByEmail(String professionalEmail) {
//    IProfessional professional = professionalInscriptionConfirmationRepository.findProfessionalByEmail(professionalEmail).orElse(null);
//
//    if(professional == null)
//      return null;
//
//    return professional;
//  }
//
//  /**
//   * Vérification que le compte n'est pas activé
//   * @param professional
//   * @return
//   */
//  public boolean isProfessionalAccountConfirmed(IProfessional professional) {
//    return professional.isProfessionalAccountConfirmed();
//  }
//
//  /**
//   * Récupération des données pour confirmer l'inscription
//   * @param professionalId Long
//   * @return IProfessionalConfirmationToken
//   */
//  public IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken findProfessionalConfirmationToken(Long professionalId) {
//    IProfessionalInscriptionConfirmation.IProfessionalConfirmationToken professionalConfirmationToken = professionalInscriptionConfirmationRepository
//            .findProfessionalConfirmationToken(professionalId).orElse(null);
//
//    if(professionalConfirmationToken == null)
//      return null;
//
//    return professionalConfirmationToken;
//  }
//
//  /**
//   * Vérification si date de confirmation valide
//   * @param activateLimitDateFomDatabase LocalDateTime
//   * @return boolean
//   */
//  public Boolean isConfirmationLimitTimeValid(LocalDateTime activateLimitDateFomDatabase, LocalDateTime actualDate) {
//    return actualDate.isBefore(activateLimitDateFomDatabase);
//  }
//
//  /**
//   * Validation Des tokens
//   * @param emailTokenToValidate _ ITokenToValidate
//   * @param urlTokenToValidate _ ITokenToValidate
//   * @return boolean
//   */
//  public boolean areProfessionalTokenValid(ITokenToValidate emailTokenToValidate, ITokenToValidate urlTokenToValidate) {
//
//    boolean isEmailTokenValid = validateTokenService.validateHashToken(emailTokenToValidate);
//    boolean isUrlTokenValid = validateTokenService.validateHashToken(urlTokenToValidate);
//    return isEmailTokenValid && isUrlTokenValid;
//  }
//
//  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl> implements UseCase.Input {
//
//    public Input(IBoundaryInputAdapter inputBoundaryAdapter) {
//      super(inputBoundaryAdapter);
//    }
//    public static Input getUseCaseInput(IBoundaryInputAdapter inputBoundaryAdapter) {
//      return new Input(inputBoundaryAdapter);
//    }
//
//    @Override
//    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter inputBoundaryAdapter) {
//
//      return BoundaryInputImpl.getBoundaryInputImpl(
//              inputBoundaryAdapter.getProfessionalEmail(),
//              inputBoundaryAdapter.getProfessionalEmailToken(),
//              inputBoundaryAdapter.getProfessionalUrlToken()
//      );
//    }
//  }
//
//  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundariesAdapter.IBoundaryOutputAdapter> implements UseCase.Output {
//
//    public Output(BoundaryOutputImpl outputBoundary) {
//      super(outputBoundary);
//    }
//
//    public static Output getUseCaseOutput(BoundaryOutputImpl boundaryOutput) {
//      return new Output(boundaryOutput);
//    }
//  }
//}
