package com.ctoutweb.lalamiam.core.useCase.impl;

import com.ctoutweb.lalamiam.core.constant.ApplicationConstant;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary.BoundaryInputImpl;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary.BoundaryOutputImpl;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.provider.INotificationService;
import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.InputBase;
import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryOutputAdapter;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription.ICreatedProfessionalAccount;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.useCase.OutputBase;
import com.ctoutweb.lalamiam.core.useCase.UseCase;

import java.time.LocalDateTime;

public class ProfessionalInscriptionUseCase implements UseCase<ProfessionalInscriptionUseCase.Input, ProfessionalInscriptionUseCase.Output> {
  private final IProfessionalInscriptionRepository professionalInscriptionRepository;
 private final ClientInscriptionUseCase clientInscriptionUseCase;
 private final INotificationService emailService;
 private final IMessageService messageService;
  public ProfessionalInscriptionUseCase(
          ClientInscriptionUseCase clientInscriptionUseCase,
          IProfessionalInscriptionRepository professionalInscriptionRepository,
          INotificationService emailService,
          IMessageService messageService
  ) {
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.professionalInscriptionRepository = professionalInscriptionRepository;
    this.emailService = emailService;
    this.messageService = messageService;
  }

  @Override
  public Output execute(Input input) {
    BoundaryInputImpl inputBoundary = input.getBoundaryInput();

    /**
     * Création d'un compte client
     * L'identifiant sera utilisé pour pouvoir faire l'inscription du professionel
     */
    long registerClienId = performClientInscription(inputBoundary);

   // Création d'un professionnel
    long professionalId  = registerProfessional(registerClienId);

   // Création du compte professionel
    createProfessionalAccount(professionalId);

//    // Génération d'une URL pour confirmer la création du compte
//    InscriptionConfirmationElement inscriptionConfirmationElement = professionalInscriptionService.createInscriptionConfirmationElement(userInscription);
//
//
//    professionalInscriptionService.sendEmailConfirmation();
//
//    return new Output(professionalInscriptionResult);

    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryInputImpl(
            messageService.getMessage("register.success"),
            professionalId
    );
    return Output.getUseCaseOutput(boundaryOutput);
  }

  /**
   * Enregistremet du professionel en tant que client
   * @param professionalInputBoundary InputBoundary
   * @return Long - Identifiant du client
   */
  public Long performClientInscription(BoundaryInputImpl professionalInputBoundary) {
    ClientInscriptionUseCase.Input clientInscriptionInput = ClientInscriptionUseCase.Input.getUseCaseInput(professionalInputBoundary);
    ClientInscriptionUseCase.Output clientInscriptionOutput = clientInscriptionUseCase.execute(clientInscriptionInput);

    return clientInscriptionOutput.getOutputBoundaryAdapter().getUserId();
  }

  /**
   * Enregistrement du profesionnel
   * @param clientId Long - Identifiant client
   * @return Long - Idenditifiant Professionnel
   */
  public Long registerProfessional(long clientId) {
    ICreatedProfessional createdProfessionalAdapter = professionalInscriptionRepository.addProfessional(clientId);
    return createdProfessionalAdapter.getProfessionalId();
  }

  /**
   * Création d'un compte pour professionnel
   * @param professionalId Long
   * @return Long - Identifiant du compte pro
   */
  public Long createProfessionalAccount(long professionalId) {
    boolean isAccountActif = ApplicationConstant.DEFAULT_VALUE_PROFESSIONAL_ACTIVATION_ACCOUNT;
    LocalDateTime creationAccountDate = LocalDateTime.now();
    ICreatedProfessionalAccount createdProfessionalAccount = professionalInscriptionRepository.addProfessionalAccount(
            professionalId, isAccountActif, creationAccountDate
    );
    return createdProfessionalAccount.getAccountId();
  }

  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl> implements UseCase.Input {

    public Input(IBoundaryInputAdapter boundaryInputAdapter) {
      super(boundaryInputAdapter);
    }
    @Override
    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter inputBoundaryAdapter) {
      return BoundaryInputImpl.getBoundaryInputImpl(
              inputBoundaryAdapter.getHashPassword(),
              inputBoundaryAdapter.getEmail(),
              inputBoundaryAdapter.getName(),
              inputBoundaryAdapter.getFirstName(),
              inputBoundaryAdapter.getPhone(),
              inputBoundaryAdapter.getDocuments(),
              inputBoundaryAdapter.getCaptchaResponseByUser(),
              inputBoundaryAdapter.getHashOrDecrypteCaptchaResponse(),
              inputBoundaryAdapter.getCryptographicType()
      );
    }
  }

  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {

    public Output(BoundaryOutputImpl boundaryOutput) {
      super(boundaryOutput);
    }

    public static Output getUseCaseOutput(BoundaryOutputImpl boundaryOutput) {
      return new Output(boundaryOutput);
    }
  }
}
