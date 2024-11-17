package useCase.impl;

import adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryInputAdapter;
import adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryOutputAdapter;
import entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument.IProfessionalDocument;
import entity.adminDisplayProfessionalDocument.impl.boundaries.BoundaryInputImpl;
import entity.adminDisplayProfessionalDocument.impl.boundaries.BoundaryOutputImpl;
import entity.common.IProfessional;
import exception.BadRequestException;
import provider.IAdminDisplayProfessionalDocumentRepository;
import provider.IMessageService;
import provider.INotificationService;
import useCase.InputBase;
import useCase.OutputBase;
import useCase.UseCase;

public class AdminDisplayProfessionalInscriptionDocumentUseCase implements UseCase<AdminDisplayProfessionalInscriptionDocumentUseCase.Input, AdminDisplayProfessionalInscriptionDocumentUseCase.Output> {
  private final IMessageService messageService;
  private final INotificationService notificationService;
  private final IAdminDisplayProfessionalDocumentRepository adminDisplayProfessionalDocumentRepository;
  public AdminDisplayProfessionalInscriptionDocumentUseCase(
          IMessageService messageService,
          INotificationService notificationService,
          IAdminDisplayProfessionalDocumentRepository adminDisplayProfessionalDocumentRepository
  ) {
    this.messageService = messageService;
    this.notificationService = notificationService;
    this.adminDisplayProfessionalDocumentRepository = adminDisplayProfessionalDocumentRepository;
  }

  @Override
  public Output execute(Input input) {
    BoundaryInputImpl inputBoundary = input.getBoundaryInput();
    Long professionalId = inputBoundary.getProfessionalId();

    var professional = findProfessional(professionalId);

    if(professional == null) {
      throw new BadRequestException(messageService.getMessage("professional.not.find"));
    }

    var professionalInscriptionDocument = findProfessionalInscriptionDocument(professionalId);

    if(professionalInscriptionDocument == null) {
      throw new BadRequestException(messageService.getMessage("professional.document.not.find"));
    }

    var outputBoundary = BoundaryOutputImpl.getBoundaryOutput(professionalInscriptionDocument);

    return Output.getUseCaseOutput(outputBoundary);
  }

  /**
   * Vérification exitance du professionnel
   * @param professionalId Long
   * @return Professional
   */
  public IProfessional findProfessional(Long professionalId) {
    IProfessional professional = adminDisplayProfessionalDocumentRepository.findProfessionalById(professionalId).orElse(null);

    if(professional == null)
      return null;

    return professional;
  }

  /**
   * Récupération des données d'inscription
   * @param professionalId Long
   * @return ProfessionalInscriptionDocument
   */
  public IProfessionalDocument findProfessionalInscriptionDocument(Long professionalId) {
    IProfessionalDocument professionalDocument = adminDisplayProfessionalDocumentRepository.findProfessionalInscriptionDocument(professionalId).orElse(null);

    if(professionalDocument == null)
      return null;

    return professionalDocument;
  }

  /**
   * Entrée dans le useCase
   */
  public static class Input extends InputBase<IBoundaryInputAdapter, BoundaryInputImpl> implements UseCase.Input {

    public Input(IBoundaryInputAdapter inputBoundaryAdapter) {
      super(inputBoundaryAdapter);
    }

    public static Input getUseCaseInput(IBoundaryInputAdapter inputBoundaryAdapter) {
      return new Input(inputBoundaryAdapter);
    }
    @Override
    protected BoundaryInputImpl getImplementation(IBoundaryInputAdapter inputBoundaryAdapter) {
      return new BoundaryInputImpl(this.inputBoundaryAdapter);
    }
  }

  /**
   * Sortie du usceCase
   */
  public static class Output extends OutputBase<BoundaryOutputImpl, IBoundaryOutputAdapter> implements UseCase.Output {

    public Output(BoundaryOutputImpl outputBoundary) {
      super(outputBoundary);
    }

    /**
     * Re
     * @param outputBoundary
     * @return
     */
    public static Output getUseCaseOutput(BoundaryOutputImpl outputBoundary) {
      return new Output(outputBoundary);
    }
  }
}

