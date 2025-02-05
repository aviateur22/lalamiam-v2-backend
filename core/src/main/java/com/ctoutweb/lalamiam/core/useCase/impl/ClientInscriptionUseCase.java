//package com.ctoutweb.lalamiam.core.useCase.impl;
//
//import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
//import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryOutputAdapter;
//import com.ctoutweb.lalamiam.core.annotation.CoreService;
//import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedClient;
//import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedAccount;
//import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedRole;
//import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryInputImpl;
//import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryOutputImpl;
//import com.ctoutweb.lalamiam.core.exception.ConflictException;
//import com.ctoutweb.lalamiam.core.provider.IMessageService;
//import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.IClientInscriptionRepository;
//import com.ctoutweb.lalamiam.core.provider.INotificationService;
//import com.ctoutweb.lalamiam.core.useCase.InputBase;
//import com.ctoutweb.lalamiam.core.useCase.OutputBase;
//import com.ctoutweb.lalamiam.core.useCase.UseCase;
//
//@CoreService
//public class ClientInscriptionUseCase implements UseCase<ClientInscriptionUseCase.Input, ClientInscriptionUseCase.Output> {
//  private final IMessageService messageService;
//  private final INotificationService notificationService;
//  private final IClientInscriptionRepository clientInscriptionRepository;
//  public ClientInscriptionUseCase(
//          IMessageService messageService,
//          INotificationService notificationService,
//          IClientInscriptionRepository userInscriptionRepository) {
//    this.messageService = messageService;
//    this.notificationService = notificationService;
//    this.clientInscriptionRepository = userInscriptionRepository;
//  }
//  @Override
//  public Output execute(Input input) throws ConflictException {
//    BoundaryInputImpl clientInscriptionInformation = input.getBoundaryInput();
//
//    // Vérification Existence email
//    if(!this.isEmailAvailable(clientInscriptionInformation.getEmail()))
//      throw new ConflictException(messageService.getMessage("email.exist"));
//
//    // Ajout de l'utilisateur
//    long createClientId = this.createClient(clientInscriptionInformation);
//
//    ICreatedRole createdRole = this.createClientRole(createClientId);
//
//    // Ajout d'un nouveau compte
//    this.createClientAccount(createClientId);
//
//    // Envoie d'un email
//    notificationService.sendMessage();
//
//    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryOutputImpl(
//            messageService.getMessage("register.success"),
//            createClientId
//    );
//
//    //return new Output(addClientId);
//    return Output.getUseCaseOutput(boundaryOutput);
//  }
//
////  public ValidateCaptchaResponseUseCase.Output executeCaptchaValidateClientResponseUseCase(IBoundaryInputAdapter boundaryInputAdapter) {
//////    // Donnéd a injecté dans le useCase CaptchaValidateClientResponseUseCase
//////    CaptchaValidateClientResponseUseCase.Input captchaInput = new CaptchaValidateClientResponseUseCase.Input(clientInscriptionAdapter, captchaService);
//////
//////    return this.captchaValidateClientResponseUseCase.execute(captchaInput);
////    return null;
////  }
//
//  /**
//   * Vérification diponibilté email
//   * @param emailClient String
//   * @return boolean
//   */
//  public boolean isEmailAvailable(String emailClient) {
//    return clientInscriptionRepository.isEmailAvailable(emailClient) == null;
//  }
//
//  /**
//   * Création d'un nouveau client
//   * @param clientInscriptionInformation ClientInscriptionInformation
//   * @return Long
//   */
//  public long createClient(BoundaryInputImpl clientInscriptionInformation) {
//    ICreatedClient addedClient = clientInscriptionRepository.addClient(
//            clientInscriptionInformation.getEmail(),
//            clientInscriptionInformation.getHashPassword(),
//            clientInscriptionInformation.getNickName()
//    );
//    // Ajout du role client
//    createClientRole(addedClient.getClientId());
//    return addedClient.getClientId();
//  }
//
//  /**
//   * Ajout du role client
//   * @param createClientId
//   * @return ICreatedRole
//   */
//  public ICreatedRole createClientRole(long createClientId) {
//    return this.clientInscriptionRepository.createRoleClient(createClientId, 1);
//  }
//
//  /**
//   * Creation compte client
//   * @param clientId long
//   * @return long
//   */
//  public long createClientAccount(long clientId) {
//    ICreatedAccount createAccount = clientInscriptionRepository.createAccountClient(clientId);
//    return createAccount.getAccountId();
//  }
//
//  /**
//   * Concrete implementation of UseCase.Input.
//   * IBoundaryInputAdapter Abstraction of the boundaryAdapter
//   * BoundaryInputImpl specific implementation of the boundaryAdapter for the useCase
//   */
//  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl> implements UseCase.Input {
//
//    public Input(IBoundaryInputAdapter boundaryInputAdapter) {
//      super(boundaryInputAdapter);
//    }
//
//    public static Input getUseCaseInput(IBoundaryInputAdapter boundaryInputAdapter) {
//      return new Input(boundaryInputAdapter);
//    }
//
//    /**
//     * specific implementation of the boundaryAdapter for the useCase
//     * @param inputBoundaryAdapter
//     * @return BoundaryInputImpl
//     */
//    @Override
//    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter inputBoundaryAdapter) {
//      return BoundaryInputImpl.getBoundaryInputImpl(
//              inputBoundaryAdapter.getHashPassword(),
//              inputBoundaryAdapter.getEmail(),
//              inputBoundaryAdapter.getNickName()
//      );
//    }
//  }
//
//  /**
//   * USeCase Output
//   */
//  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {
//    public Output(BoundaryOutputImpl outputBoundary) {
//      super(outputBoundary);
//    }
//
//    public static Output getUseCaseOutput(BoundaryOutputImpl outputBoundary) {
//      return new Output(outputBoundary);
//    }
//  }
//}
