package com.ctoutweb.lalamiam.core.useCase.professionalInscription.factory;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.impl.ProfessionalInscriptionOutputImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.IProfessionalInscription;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.impl.ProfessionalInscriptionImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.useCase.ProfessionalInscriptionUseCase;

public class Factory {

  public static IProfessionalInscription getProfessionalInscriptionImpl(
          IProfessionalInscriptionInput professionalInscriptionInformation,
          ClientInscriptionUseCase clientInscriptionUseCase,
          IMessageService messageService
  ) {
    return new ProfessionalInscriptionImpl(professionalInscriptionInformation, clientInscriptionUseCase, messageService);
  }
  public static IProfessioanlInscriptionOutput getProfessionalInscriptionOutputImpl(Long professionalId, String responseMessage) {
    return new ProfessionalInscriptionOutputImpl(professionalId, responseMessage);
  }
  public static ProfessionalInscriptionUseCase.Output getUseCaseOutput(IProfessioanlInscriptionOutput professioanlInscriptionOutput) {
    return new ProfessionalInscriptionUseCase.Output(professioanlInscriptionOutput);
  }
}
