package com.ctoutweb.lalmiam.core.usecase.adminDisplayProfessionalInscriptionDocument;

import com.ctoutweb.lalamiam.core.adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryInputAdapter;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.common.IProfessional;
import com.ctoutweb.lalamiam.core.entity.common.impl.ProfessionalImpl;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.impl.entity.ProfessionalInscriptionDocumentImpl;
import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.provider.IAdminDisplayProfessionalDocumentRepository;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.provider.INotificationService;
import com.ctoutweb.lalamiam.core.useCase.impl.AdminDisplayProfessionalInscriptionDocumentUseCase;

import java.util.Optional;

public class AdminDisplayProfessionalInscriptionDocumentUseCaseTest {

  @Mock
  IAdminDisplayProfessionalDocumentRepository adminDisplayProfessionalDocumentRepository;
  @Mock
  IMessageService messageService;
  @Mock
  INotificationService notificationService;

  AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    adminDisplayProfessionalInscriptionDocumentUseCase = new AdminDisplayProfessionalInscriptionDocumentUseCase(
            messageService,
            notificationService,
            adminDisplayProfessionalDocumentRepository
    );
  }

  @Test
  public void AdminDisplayProfessionalInscriptionDocumentUseCase_should_throw_when_professional_not_find() {

    /**
     * given
     */
    String exceptionMessage = "professionnel pas trouvé";
    Mockito.when(messageService.getMessage("professional.not.find")).thenReturn(exceptionMessage);
    IBoundaryInputAdapter boundaryInputAdapter = DataForTest.fakeInputAdapter();
    var input = AdminDisplayProfessionalInscriptionDocumentUseCase.Input.getUseCaseInput(boundaryInputAdapter);
    Mockito.when(adminDisplayProfessionalDocumentRepository.findProfessionalById(Mockito.any())).thenReturn(Optional.empty());


    /**
     * when
     */
    Exception exception = Assertions.assertThrows(BadRequestException.class, ()->adminDisplayProfessionalInscriptionDocumentUseCase.execute(input));
    String actualExceptionMessage = exception.getMessage();

    /**
     * then
     */
    Assertions.assertEquals(exceptionMessage, actualExceptionMessage);
  }

  @Test
  public void AdminDisplayProfessionalInscriptionDocumentUseCase_should_throw_when_professional_document_not_find() {

    /**
     * given
     */
    String exceptionMessage = "document du professionnel pas trouvé";

    // Mock du message d'erreur
    Mockito.when(messageService.getMessage("professional.document.not.find")).thenReturn(exceptionMessage);
    IBoundaryInputAdapter boundaryInputAdapter = DataForTest.fakeInputAdapter();
    var input = AdminDisplayProfessionalInscriptionDocumentUseCase.Input.getUseCaseInput(boundaryInputAdapter);

    // Mock la recherche du professionel
    Mockito.when(adminDisplayProfessionalDocumentRepository.findProfessionalById(Mockito.any())).thenReturn(Optional.of(DataForTest.fakeProfessionalWithAccountNotConfirmed()));

    // Mock la recherche des docuements d'inscription
    Mockito.when(adminDisplayProfessionalDocumentRepository.findProfessionalInscriptionDocument(Mockito.any())).thenReturn(Optional.empty());


    /**
     * when
     */
    Exception exception = Assertions.assertThrows(BadRequestException.class, ()->adminDisplayProfessionalInscriptionDocumentUseCase.execute(input));
    String actualExceptionMessage = exception.getMessage();

    /**
     * then
     */
    Assertions.assertEquals(exceptionMessage, actualExceptionMessage);
  }
  @Test
  public void findProfessional_should_find_professional() {
    /**
     * given
     */
    Long professionalId = 1l;
    IProfessional professional = DataForTest.fakeProfessionalWithAccountConfirmed();
    Mockito.when(adminDisplayProfessionalDocumentRepository.findProfessionalById(professionalId)).thenReturn(Optional.of(professional));

    /**
     * when
     */
    IProfessional actualResponse = adminDisplayProfessionalInscriptionDocumentUseCase.findProfessional(professionalId);
    ProfessionalImpl expectedResponse = DataForTest.fakeProfessionalWithAccountActive();

    /**
     * then
     */
    Assertions.assertEquals(expectedResponse.isProfessionalAccountConfirmed(), actualResponse.isProfessionalAccountConfirmed());
    Assertions.assertEquals(expectedResponse.getProfessionalId(), actualResponse.getProfessionalId());
  }

  @Test
  public void findProfessional_should_return_null_when_professional_not_found() {
    /**
     * given
     */
    Long professionalId = 1l;
    IProfessional professional = DataForTest.fakeProfessionalWithAccountConfirmed();
    Mockito.when(adminDisplayProfessionalDocumentRepository.findProfessionalById(professionalId)).thenReturn(Optional.empty());

    /**
     * when
     */
    IProfessional actualResponse = adminDisplayProfessionalInscriptionDocumentUseCase.findProfessional(professionalId);
    ProfessionalImpl expectedResponse = null;

    /**
     * then
     */
    Assertions.assertNull(actualResponse);
  }

  @Test
  public void findProfessionalInscriptionDocument_should_give_all_documents() {
    /**
     * given
     */
    Long professionalId = 1l;
    IAdminDisplayProfessionalDocument.IProfessionalDocument professionalInscriptionDocument = DataForTest.fakeProfessionalInscriptionDocument();
    Mockito.when(adminDisplayProfessionalDocumentRepository.findProfessionalInscriptionDocument(professionalId)).thenReturn(Optional.of(professionalInscriptionDocument));

    /**
     * when
     */
    IAdminDisplayProfessionalDocument.IProfessionalDocument actualResponse = adminDisplayProfessionalInscriptionDocumentUseCase.findProfessionalInscriptionDocument(professionalId);
    ProfessionalInscriptionDocumentImpl expectedResponse = DataForTest.fakeProfessionalInscriptionDocumentImpl();

    /**
     * then
     */
    Assertions.assertEquals(expectedResponse.getProfessionalId(), actualResponse.getProfessionalId());
    Assertions.assertEquals(expectedResponse.getProfessionalEmail(), actualResponse.getProfessionalEmail());
    Assertions.assertEquals(expectedResponse.getProfessionalPhone(), actualResponse.getProfessionalPhone());
    Assertions.assertEquals(expectedResponse.getProfessionalFileStatus(), actualResponse.getProfessionalFileStatus());

  }

  @Test
  public void findProfessionalInscriptionDocument_should_throw_because_documents_are_not_find() {

  }

}

