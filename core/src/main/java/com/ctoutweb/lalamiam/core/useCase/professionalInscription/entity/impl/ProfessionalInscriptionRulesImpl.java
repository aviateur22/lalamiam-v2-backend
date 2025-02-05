package com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.impl;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.factory.Factory;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessionalAccount;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ISavedInscriptionDocuments;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.IProfessionalInscriptionRules;

import java.util.List;

public class ProfessionalInscriptionRulesImpl implements IProfessionalInscriptionRules {
  private final ClientInscriptionUseCase clientInscriptionUseCase;
  private final ICoreMessageService messageService;
  private final IProfessionalInscriptionRepository professionalInscriptionRepository;
  private final ICoreEmailService coreEmailService;
  private IProfessionalInscriptionInput professionalInscriptionInformation;
  private Long registerUserId; // creation client
  private Long registerProfessionalId; // id création pro
  private Long accountId; // id creation compte pro
  private List<Long> documentIds; // id document d'inscription

  public ProfessionalInscriptionRulesImpl(
          ClientInscriptionUseCase clientInscriptionUseCase,
          ICoreMessageService messageService,
          ICoreEmailService coreEmailService,
          IProfessionalInscriptionRepository professionalInscriptionRepository) {
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.messageService = messageService;
    this.professionalInscriptionRepository = professionalInscriptionRepository;
    this.coreEmailService = coreEmailService;
  }
  @Override
  public IProfessionalInscriptionRules createUserAccount(IProfessionalInscriptionInput professionalInscriptionInformation) {
    ClientInscriptionUseCase.Input clientInput = ClientInscriptionUseCase.Input.getInput(professionalInscriptionInformation);
    ClientInscriptionUseCase.Output output = clientInscriptionUseCase.execute(clientInput);

    // Sauvegarde des données d'inscription du professionnel
    this.professionalInscriptionInformation = professionalInscriptionInformation;

    // Enregistrement de Id client
    this.registerUserId = output.getOutputBoundary().getUserId();
    return this;
  }

  @Override
  public IProfessionalInscriptionRules registerProfessional() {
    ICreatedProfessional createdProfessional = professionalInscriptionRepository
            .addProfessional(this.registerUserId, this.professionalInscriptionInformation);

    // Enregistrement du id professionnel
    this.registerProfessionalId = createdProfessional.getProfessionalId();

    return this;
  }
  @Override
  public IProfessionalInscriptionRules createProfessionalAccount() {
    ICreatedProfessionalAccount createdProfessionalAccount = professionalInscriptionRepository
            .addProfessionalAccount(this.registerProfessionalId);

    // Enregistrement compte
    this.accountId = createdProfessionalAccount.getAccountId();
    return this;
  }

  @Override
  public IProfessionalInscriptionRules sendRegisterConfirmationEmail() {
    coreEmailService.sendProfessionalRegisterConfirmation(professionalInscriptionInformation.getEmail());
    return this;
  }

  @Override
  public IProfessionalInscriptionRules saveProfessionalInscriptionDocument() {
    ISavedInscriptionDocuments documents = professionalInscriptionRepository
            .saveProfessionalInscriptionDocument(
                    professionalInscriptionInformation.getProfessionalInscriptionDocuments(),
                    this.registerProfessionalId);

    // sauvegarde documents
    this.documentIds = documents.getInscriptionDocumentId();
    return this;
  }

  @Override
  public IProfessioanlInscriptionOutput getProfessionalInscriptionResponse() {
    return Factory.getProfessionalInscriptionOutputImpl(
            this.registerProfessionalId,
            this.accountId,
            this.documentIds,
            this.messageService.getMessage("register.success")

    );
  }
}
