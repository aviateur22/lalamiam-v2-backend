package com.ctoutweb.lalamiam.core.useCase.professionalInscription.factory;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.impl.ProfessionalInscriptionOutputImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.IProfessionalInscriptionRules;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.impl.ProfessionalInscriptionRulesImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.useCase.ProfessionalInscriptionUseCase;

import java.util.List;

public class Factory {

  public static IProfessionalInscriptionRules getProfessionalInscriptionImpl(
          IMessageService messageService,
          IProfessionalInscriptionRepository professionalInscriptionRepository,
          ICoreEmailService coreEmailService,
          ClientInscriptionUseCase clientInscriptionUseCase
  ) {
    return new ProfessionalInscriptionRulesImpl(
            clientInscriptionUseCase,
            messageService,
            coreEmailService,
            professionalInscriptionRepository);
  }
  public static IProfessioanlInscriptionOutput getProfessionalInscriptionOutputImpl(
          Long professionalId,
          Long accountId,
          List<Long> documents,
          String responseMessage) {
    return new ProfessionalInscriptionOutputImpl(professionalId, accountId, documents, responseMessage);
  }
  public static ProfessionalInscriptionUseCase.Output getUseCaseOutput(IProfessioanlInscriptionOutput professioanlInscriptionOutput) {
    return new ProfessionalInscriptionUseCase.Output(professioanlInscriptionOutput);
  }
}
