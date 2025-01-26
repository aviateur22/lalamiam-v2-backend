package com.ctoutweb.lalamiam.core.factory;

import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessioanlInscriptionConfirmationOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.impl.ProfessionalInscriptionConfirmationInputBoundaryImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.adapter.impl.ProfessionalInscriptionConfirmationOutputBoundaryImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.entity.IProfessionalInscriptionConfirmation;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.entity.impl.ProfessionalInscriptionConfirmationImpl;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionConfirmationRepository;

public class Factory {

  public static IProfessionalInscriptionConfirmationInput getProfessionalInscriptionConfirmationInputImpl(String email) {
    return new ProfessionalInscriptionConfirmationInputBoundaryImpl(email);
  }

  public static IProfessioanlInscriptionConfirmationOutput getProfessioanlInscriptionConfirmationOutputImpl(String responseMessage) {
    return new ProfessionalInscriptionConfirmationOutputBoundaryImpl(responseMessage);
  }

  public static IProfessionalInscriptionConfirmation getProfessionalInscriptionConfirmationImpl(
          IProfessionalInscriptionConfirmationInput professionalInscriptionConfirmationInputImpl,
          IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository,
          IMessageService messageService) {
    return new ProfessionalInscriptionConfirmationImpl(
            professionalInscriptionConfirmationInputImpl,
            professionalInscriptionConfirmationRepository,
            messageService
    );
  }
}
