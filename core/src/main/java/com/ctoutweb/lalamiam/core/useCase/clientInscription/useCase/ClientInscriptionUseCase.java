package com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase;

import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionOutput;

public class ClientInscriptionUseCase implements UseCase<ClientInscriptionUseCase.Input, ClientInscriptionUseCase.Output>  {
  @Override
  public Output execute(Input input) {
    return null;
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
}
