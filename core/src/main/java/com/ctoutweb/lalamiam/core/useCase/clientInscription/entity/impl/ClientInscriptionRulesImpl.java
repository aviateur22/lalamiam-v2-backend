package com.ctoutweb.lalamiam.core.useCase.clientInscription.entity.impl;

import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.entity.IClientInscriptionRules;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.factory.Factory;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedAccount;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedClient;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionOutput;

public class ClientInscriptionRulesImpl implements IClientInscriptionRules {
  private final IClientInscriptionRepository clientInscriptionRepository;
  private final ICoreMessageService messageService;
  private final ICoreEmailService emailService;

  private String clientEmail;
  private ICreatedClient createdClient; // Données sur l'enregistrement client
  private ICreatedAccount createdAccount; // Données sur le compte client

  public ClientInscriptionRulesImpl(
          IClientInscriptionRepository clientInscriptionRepository,
          ICoreMessageService messageService,
          ICoreEmailService emailService) {
    this.clientInscriptionRepository = clientInscriptionRepository;
    this.messageService = messageService;
    this.emailService = emailService;
  }

  @Override
  public IClientInscriptionRules isClientEmailAvailable(String clientEmail) {
    if(!clientInscriptionRepository.isEmailAvailable(clientEmail))
      throw new ConflictException(messageService.getMessage("email.exist"));

    // Sauvegarde de l'email client
    this.clientEmail = clientEmail;

    return this;
  }

  @Override
  public IClientInscriptionRules createClient(IClientInscriptionInput clientInformation) {
    this.createdClient = clientInscriptionRepository.addClient(
            clientInformation.getEmail(),
            clientInformation.getHashPassword(),
            clientInformation.getNickName());

    return this;
  }

  @Override
  public IClientInscriptionRules createClientAccount() {
    this.createdAccount = clientInscriptionRepository.createAccountClient(createdClient.getClientId());

    return this;
  }

  @Override
  public IClientInscriptionRules sendEmail(boolean isEmailToSend) {
    // Envoie l'email si creation d'un compte client
    if(isEmailToSend)
      this.emailService.sendClientEmailRegisterActivation(clientEmail);

    return this;
  }

  @Override
  public IClientInscriptionOutput getClientInscriptionInformation() {
    return Factory.getClientInscriptionOutputImpl(this.createdClient.getClientId(), this.createdAccount.getAccountId());
  }
}
