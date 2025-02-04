package com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.useCase;

import com.ctoutweb.lalamiam.core.annotation.CoreService;
import com.ctoutweb.lalamiam.core.factory.Factory;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessioanlInscriptionConfirmationOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.gateway.IProfessionalInscriptionConfirmationRepository;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.entity.IProfessionalInscriptionConfirmation;

import java.time.ZonedDateTime;

/**
 * Confirmation de l'inscription par le professionel
 * Cette action intervient apres la reception d'un email
 * qui contient un lien pour confirmer l'inscription
 */
@CoreService
public class ProfessionalInscriptionConfirmationUseCase implements UseCase<ProfessionalInscriptionConfirmationUseCase.Input, ProfessionalInscriptionConfirmationUseCase.Output> {
  private final IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository;
  private final IMessageService messageService;
  private IProfessionalInscriptionConfirmation professionalInscriptionConfirmation;


  public ProfessionalInscriptionConfirmationUseCase(
          IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository,
          IMessageService messageService) {
    this.professionalInscriptionConfirmationRepository = professionalInscriptionConfirmationRepository;
    this.messageService = messageService;
  }

  @Override
  public Output execute(Input input) {

    IProfessionalInscriptionConfirmationInput inputBoundaryImpl = input.getInputBoundaryImplementation();

    professionalInscriptionConfirmation = Factory.getProfessionalInscriptionConfirmationImpl(
            inputBoundaryImpl,
            professionalInscriptionConfirmationRepository,
            messageService
    );

    professionalInscriptionConfirmation
            .findProfessionalEmail()
            .isRegisterConfirmedByProfessional()
            .isProfessionalAccountActive()
            .isConfirmationDateValid(ZonedDateTime.now())
            .persistProfessionalInscriptionConfirmation();


    IProfessioanlInscriptionConfirmationOutput boundaryOutput = Factory.getProfessioanlInscriptionConfirmationOutputImpl(
            messageService.getMessage("professional.register.confirmation")
    );

    // Renvoie UseCaseOutput
    return Output.getUseCaseOutput(boundaryOutput);
  }

  public static class Input extends InputBaseNew<IProfessionalInscriptionConfirmationInput> implements UseCase.Input {
    public Input(IProfessionalInscriptionConfirmationInput inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }

    @Override
    protected IProfessionalInscriptionConfirmationInput getInputBoundaryImplementation() {
      return Factory.getProfessionalInscriptionConfirmationInputImpl(inputBoundaryAdapter.getProfessionalEmail());
    }

    /**
     * Renvoie du Input pour le useCase
     * @param inputBoundaryAdapter
     * @return ProfessionalInscriptionConfirmationUseCase.Input
     */
    public static ProfessionalInscriptionConfirmationUseCase.Input getInput(IProfessionalInscriptionConfirmationInput inputBoundaryAdapter) {
      return new ProfessionalInscriptionConfirmationUseCase.Input(inputBoundaryAdapter);
    }
  }

  public static class Output extends OutputBaseNew<IProfessioanlInscriptionConfirmationOutput> implements UseCase.Output {

    public Output(IProfessioanlInscriptionConfirmationOutput outputBoundary) {
      super(outputBoundary);
    }

    public static Output getUseCaseOutput(IProfessioanlInscriptionConfirmationOutput boundaryOutput) {
      return new Output(boundaryOutput);
    }
  }
}
