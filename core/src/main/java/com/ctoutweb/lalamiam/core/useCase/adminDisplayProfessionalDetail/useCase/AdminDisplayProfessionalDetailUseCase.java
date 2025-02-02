package com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.useCase;

import com.ctoutweb.lalamiam.core.annotation.CoreService;
import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.UseCase;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailInput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailOutput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailRepository;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.factory.Factory;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.InputBaseNew;
import com.ctoutweb.lalamiam.core.useCase.base.useCase.OutputBaseNew;

@CoreService
public class AdminDisplayProfessionalDetailUseCase implements UseCase<AdminDisplayProfessionalDetailUseCase.Input, AdminDisplayProfessionalDetailUseCase.Output> {

  private final IAdminDisplayProfessionalDetailRepository adminDisplayProfessionalDetailRepository;
  private final ICoreMessageService messageService;
  private final ICoreEmailService emailService;

  public AdminDisplayProfessionalDetailUseCase(IAdminDisplayProfessionalDetailRepository adminDisplayProfessionalDetailRepository, ICoreMessageService messageService, ICoreEmailService emailService) {
    this.adminDisplayProfessionalDetailRepository = adminDisplayProfessionalDetailRepository;
    this.messageService = messageService;
    this.emailService = emailService;
  }

  @Override
  public Output execute(Input input) {
    String professionalEmail =  input.getInputBoundaryImplementation().getEmail();

    IProfessionalDetail professionalDetail = adminDisplayProfessionalDetailRepository.findProfessionalDetail(professionalEmail);

    if(professionalDetail ==  null)
      throw new BadRequestException(messageService.getMessage("professional.not.find"));

    // Confirmation de l'enregistrement compte pas encore validé
    if(!professionalDetail.getIsAccountRegisterConfirm())
      throw new BadRequestException(messageService.getMessage("professional.register.account.not.confirm"));

    // Compte pro déja validé par un admin
    if(professionalDetail.getIsAccountActivate())
      throw new BadRequestException(messageService.getMessage("professional.account.already.activated"));

    // Envoie un email d'activation de compte professional
    this.emailService.sendActivateProfessionalAccountEmail(professionalEmail);

    var adminDisplayProfessionalDetailOutput = Factory.getAdminDisplayProfessionalDetailOutputImpl(professionalDetail);

    return Factory.getOutput(adminDisplayProfessionalDetailOutput);
  }

  public static class Input extends InputBaseNew<IAdminDisplayProfessionalDetailInput> implements UseCase.Input {

    public Input(IAdminDisplayProfessionalDetailInput inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }

    @Override
    protected IAdminDisplayProfessionalDetailInput getInputBoundaryImplementation() {
      return this.inputBoundaryAdapter;
    }

    public static AdminDisplayProfessionalDetailUseCase.Input getInput(IAdminDisplayProfessionalDetailInput inputBoundaryAdapter) {
      return new Input(inputBoundaryAdapter);
    }
  }

  public static class Output extends OutputBaseNew<IAdminDisplayProfessionalDetailOutput> implements UseCase.Output {
    public Output(IAdminDisplayProfessionalDetailOutput outputBoundary) {
      super(outputBoundary);
    }
  }
}
