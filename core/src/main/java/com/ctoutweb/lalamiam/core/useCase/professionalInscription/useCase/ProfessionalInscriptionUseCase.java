package com.ctoutweb.lalamiam.core.useCase.professionalInscription.useCase;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessioanlInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.IProfessionalInscriptionRules;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.factory.Factory;

public class ProfessionalInscriptionUseCase implements UseCase<ProfessionalInscriptionUseCase.Input, ProfessionalInscriptionUseCase.Output> {
  private final IProfessionalInscriptionRules professionalInscriptionRules;
  public ProfessionalInscriptionUseCase(
          ClientInscriptionUseCase clientInscriptionUseCase,
          IMessageService messageService,
          IProfessionalInscriptionRepository professionalInscriptionRepository,
          ICoreEmailService coreEmailService) {
    this.professionalInscriptionRules = Factory.getProfessionalInscriptionImpl(
            messageService,
            professionalInscriptionRepository,
            coreEmailService,
            clientInscriptionUseCase);
  }
  @Override
  public Output execute(Input input) {
    // Données du professionel
    IProfessionalInscriptionInput professionalInscriptionInformation =  input.getInputBoundaryImplementation();

    IProfessioanlInscriptionOutput output= professionalInscriptionRules
            .isProfessionalEmailAvailable(professionalInscriptionInformation)
            .createUserAccount()
            .registerProfessional()
            .saveProfessionalInscriptionDocument()
            .createProfessionalAccount()
            .sendRegisterConfirmationEmail()
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
