package com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.factory;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.entity.IClientRegisterAsProfessionalRules;
import com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.entity.impl.ClientRegisterAsProfessionalRulesImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;

public class Factory {
  public static IClientRegisterAsProfessionalRules getClientRegisterAsProfessionalRulesImpl(
          ICoreMessageService messageService, ICoreEmailService emailService, IProfessionalInscriptionRepository repository) {
    return new ClientRegisterAsProfessionalRulesImpl(messageService, emailService, repository);
  }
}
