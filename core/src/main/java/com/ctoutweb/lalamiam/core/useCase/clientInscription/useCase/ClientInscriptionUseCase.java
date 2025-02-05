package com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase;

import com.ctoutweb.lalamiam.core.annotation.CoreService;
import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.entity.IClientInscriptionRules;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.factory.Factory;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionOutput;

@CoreService
public class ClientInscriptionUseCase implements UseCase<ClientInscriptionUseCase.Input, ClientInscriptionUseCase.Output>  {
  IClientInscriptionRules clientInscriptionRules;
  private boolean isEmailToSend;
  public ClientInscriptionUseCase(
          IClientInscriptionRepository clientInscriptionRepository,
          ICoreEmailService emailService,
          ICoreMessageService messageService) {
    clientInscriptionRules = Factory.getClientInscriptionRulesImpl(
            clientInscriptionRepository, messageService, emailService);
  }

  @Override
  public Output execute(Input input) {
    IClientInscriptionInput clientInformation = input.getInputBoundaryImplementation();

    var output = clientInscriptionRules
            .isClientEmailAvailable(clientInformation.getEmail())
            .createClient(clientInformation)
            .createClientAccount()
            .sendEmail(this.isEmailToSend)
            .getClientInscriptionInformation();

    return Factory.getOutputImpl(output);
  }
  public static class Input extends InputBaseNew<IClientInscriptionInput> implements UseCase.Input {

    public static ClientInscriptionUseCase.Input getInput(IClientInscriptionInput clientInscriptionInformation) {
      return new Input(clientInscriptionInformation);
    }
    public Input(IClientInscriptionInput inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }
    @Override
    protected IClientInscriptionInput getInputBoundaryImplementation() {
      return null;
    }
  }

  public static class Output extends OutputBaseNew<IClientInscriptionOutput> implements UseCase.Output {
    public Output(IClientInscriptionOutput outputBoundary) {
      super(outputBoundary);
    }
  }

  ///////////////////////////////////////////////////////////////////////////
  public boolean isEmailToSend() {
    return isEmailToSend;
  }

  public void setEmailToSend(boolean emailToSend) {
    isEmailToSend = emailToSend;
  }

}
