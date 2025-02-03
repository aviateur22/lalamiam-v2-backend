package com.ctoutweb.lalamiam.core.useCase.professionalInscription.useCase;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.adapter.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.IProfessionalInscription;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.factory.Factory;

public class ProfessionalInscriptionUseCase implements UseCase<ProfessionalInscriptionUseCase.Input, ProfessionalInscriptionUseCase.Output> {

  private final IMessageService messageService;
  private final ClientInscriptionUseCase clientInscriptionUseCase;
  public ProfessionalInscriptionUseCase(ClientInscriptionUseCase clientInscriptionUseCase, IMessageService messageService) {
    this.clientInscriptionUseCase = clientInscriptionUseCase;
    this.messageService = messageService;

  }

  @Override
  public Output execute(Input input) {
    // Données du professionel
    IProfessionalInscriptionInput professionalInscriptionInformation =  input.getInputBoundaryImplementation();

    IProfessionalInscription professionalInscription = Factory.getProfessionalInscriptionImpl(
            professionalInscriptionInformation, clientInscriptionUseCase, messageService);

    IProfessioanlInscriptionOutput output= professionalInscription
            .isProfessionalEmailAvailable()
            .createUserAccount()
            .registerProfessional()
            .saveProfessionalInscriptionDocument()
            .createProfessionalAccount()
            .getProfessionalInscriptionResponse();

    return Factory.getUseCaseOutput(output);
  }

  public static class Input extends InputBaseNew<IProfessionalInscriptionInput> implements UseCase.Input {
    public Input(IProfessionalInscriptionInput inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }
    @Override
    protected IProfessionalInscriptionInput getInputBoundaryImplementation() {
      return null;
    }
  }

  public static class Output extends OutputBaseNew<IProfessioanlInscriptionOutput> implements UseCase.Output {

    public Output(IProfessioanlInscriptionOutput outputBoundary) {
      super(outputBoundary);
    }
  }
}
