package com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.useCase;

import com.ctoutweb.lalamiam.core.annotation.CoreService;
import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.entity.IClientRegisterAsProfessionalRules;
import com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.factory.Factory;
import com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.port.IClientRegisterAsProfessionalInput;
import com.ctoutweb.lalamiam.core.useCase.clientRegisterAsProfessional.port.IClientRegisterAsProfessionalOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;

@CoreService
public class ClientRegisterAsProfessionalUseCase implements UseCase<ClientRegisterAsProfessionalUseCase.Input, ClientRegisterAsProfessionalUseCase.Output> {
  private final IClientRegisterAsProfessionalRules clientRegisterAsProfessionalRules;
  public ClientRegisterAsProfessionalUseCase(
          ICoreMessageService messageService,
          IProfessionalInscriptionRepository professionalInscriptionRepository,
          ICoreEmailService emailService
  ) {
    this.clientRegisterAsProfessionalRules = Factory.getClientRegisterAsProfessionalRulesImpl(
            messageService, emailService, professionalInscriptionRepository
    );
  }

  @Override
  public Output execute(Input input) {
    return null;
  }

  public static class Input extends InputBaseNew<IClientRegisterAsProfessionalInput> implements UseCase.Input {
    public Input(IClientRegisterAsProfessionalInput inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }
    @Override
    protected IClientRegisterAsProfessionalInput getInputBoundaryImplementation() {
      return this.inputBoundaryAdapter;
    }
  }

  public static class Output extends OutputBaseNew<IClientRegisterAsProfessionalOutput> implements UseCase.Output {
    public Output(IClientRegisterAsProfessionalOutput outputBoundary) {
      super(outputBoundary);
    }
  }
}
