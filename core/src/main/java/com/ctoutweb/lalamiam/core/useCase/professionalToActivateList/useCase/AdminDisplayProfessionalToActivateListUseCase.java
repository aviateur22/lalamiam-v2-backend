package com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.useCase;

import com.ctoutweb.lalamiam.core.annotation.CoreService;
import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter.IProfessionalToActivateListInput;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter.IProfessionalToActivateListOutput;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.adapter.IProfessionalToActivateRepository;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.entity.IProfessionalToActivate;
import com.ctoutweb.lalamiam.core.useCase.professionalToActivateList.factory.Factory;

import java.util.List;

/**
 * Récupération des nouveau compte professionel a activer
 */
@CoreService
public class AdminDisplayProfessionalToActivateListUseCase
        implements UseCase<AdminDisplayProfessionalToActivateListUseCase.Input, AdminDisplayProfessionalToActivateListUseCase.Output> {
  private final IProfessionalToActivateRepository professionalToActivateRepository;

  public AdminDisplayProfessionalToActivateListUseCase(IProfessionalToActivateRepository professionalToActivateRepository) {
    this.professionalToActivateRepository = professionalToActivateRepository;
  }

  @Override
  public Output execute(Input input) {
    List<IProfessionalToActivate> professionalToActivateList = professionalToActivateRepository.getProfessionalToActivateList();
    IProfessionalToActivateListOutput output = Factory.getProfessionalToActivateListOutputImpl(professionalToActivateList);
    return Factory.getUseCaseOutput(output);
  }

  public static class Input extends InputBaseNew<IProfessionalToActivateListInput> implements UseCase.Input {

    public Input(IProfessionalToActivateListInput inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }

    @Override
    protected IProfessionalToActivateListInput getInputBoundaryImplementation() {
      return this.inputBoundaryAdapter;
    }

    /**
     * Renvoie Input du Usecase sans parametre particulier
     */
    public static AdminDisplayProfessionalToActivateListUseCase.Input getInput() {
      return new Input(null);
    }
  }

  public static class Output extends OutputBaseNew<IProfessionalToActivateListOutput> implements UseCase.Output {

    public Output(IProfessionalToActivateListOutput outputBoundary) {
      super(outputBoundary);
    }
  }
}
