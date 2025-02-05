package com.ctoutweb.lalamiam.core.useCase.clientInscription.factory;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.entity.IClientInscriptionRules;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.entity.impl.ClientInscriptionRulesImpl;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.impl.ClientInscriptionInputImpl;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.impl.ClientInscriptionOutputImpl;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase.ClientInscriptionUseCase;

public class Factory {

  public static IClientInscriptionRules getClientInscriptionRulesImpl(
          IClientInscriptionRepository repository,
          ICoreMessageService messageService,
          ICoreEmailService emailService) {
    return new ClientInscriptionRulesImpl(repository, messageService, emailService);
  }
  public static ClientInscriptionUseCase.Output getOutputImpl(IClientInscriptionOutput clientInscriptionOutput) {
    return new ClientInscriptionUseCase.Output(clientInscriptionOutput);
  }

  public static IClientInscriptionOutput getClientInscriptionOutputImpl(Long clientId, Long accountId) {
    return new ClientInscriptionOutputImpl(clientId, accountId);
  }
}
