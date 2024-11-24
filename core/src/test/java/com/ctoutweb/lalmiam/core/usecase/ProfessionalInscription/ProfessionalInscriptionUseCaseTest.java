package com.ctoutweb.lalmiam.core.usecase.ProfessionalInscription;


import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryOutputImpl;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.IProfessionalInscription;
import com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary.BoundaryInputImpl;
import com.ctoutweb.lalamiam.core.exception.ConflictException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.provider.INotificationService;
import com.ctoutweb.lalamiam.core.provider.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.impl.ProfessionalInscriptionUseCase;

import static common.DataForTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class ProfessionalInscriptionUseCaseTest {


  @Mock
  IProfessionalInscriptionRepository professionalInscriptionRepository;
  @Mock
  IMessageService messageService;
  @Mock
  INotificationService notificationService;
  @Mock
  ClientInscriptionUseCase clientInscriptionUseCase;
  IBoundaryInputAdapter professionalInscriptionInformationAdapter = fakeProfessionalInscriptionInformationAdapter();
  ProfessionalInscriptionUseCase professionalInscriptionUseCase;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);

    professionalInscriptionUseCase = new ProfessionalInscriptionUseCase(
            clientInscriptionUseCase,
            professionalInscriptionRepository,
            notificationService,
            messageService
    );
  }

  @Test
  public void performClientInscription_should_register_client() {
    /**
     * given
     */
    when(clientInscriptionUseCase.execute(any())).thenReturn(new ClientInscriptionUseCase.Output(new BoundaryOutputImpl("", 3l)));
    IBoundaryInputAdapter professionalInscriptionInformationAdapter = fakeProfessionalInscriptionInformationAdapter();
    BoundaryInputImpl boundaryInput = BoundaryInputImpl.getBoundaryInputImpl(
            professionalInscriptionInformationAdapter.getHashPassword(),
            professionalInscriptionInformationAdapter.getEmail(),
            professionalInscriptionInformationAdapter.getUserName(),
            professionalInscriptionInformationAdapter.getFirstName(),
            professionalInscriptionInformationAdapter.getPhone(),
            professionalInscriptionInformationAdapter.getDocuments(),
            professionalInscriptionInformationAdapter.getCaptchaResponseByUser(),
            professionalInscriptionInformationAdapter.getHashOrDecrypteCaptchaResponse(),
            professionalInscriptionInformationAdapter.getCryptographicType(),
            professionalInscriptionInformationAdapter.getCaptchaToken(),
            professionalInscriptionInformationAdapter.getCaptchaTokenSeparator()
    );

    /**
     * when
     */
    Long actualClientId = professionalInscriptionUseCase.performClientInscription(boundaryInput);
    Long expectedClientId = 3L;

    /**
     * then
     */
    Assertions.assertEquals(expectedClientId, actualClientId);
  }

  @Test
  public void ProfessionalInscriptionUseCase_should_register_professional() {
    /**
     * given
     */
    BoundaryOutputImpl clientInscriptionResponse = new BoundaryOutputImpl("", 1l);
    Mockito.when(clientInscriptionUseCase.execute(any())).thenReturn(new ClientInscriptionUseCase.Output(clientInscriptionResponse));
    Mockito.when(professionalInscriptionRepository.addProfessional(any())).thenReturn(new IProfessionalInscription.ICreatedProfessional() {
      @Override
      public Long getProfessionalId() {
        return 1l;
      }
    });
    Mockito.when(professionalInscriptionRepository.addProfessionalAccount(eq(1L), eq(false), any())).thenReturn(new IProfessionalInscription.ICreatedProfessionalAccount()
    {
      @Override
      public Long getAccountId() {
        return 3l;
      }
    });
    Mockito.when(messageService.getMessage(any())).thenReturn("message réponse");

    /**
     * when
     */
    ProfessionalInscriptionUseCase.Input professionalInscriptionUseCaseInput = new ProfessionalInscriptionUseCase.Input(fakeProfessionalInscriptionInformationAdapter());
    ProfessionalInscriptionUseCase.Output actualResponse = professionalInscriptionUseCase.execute(professionalInscriptionUseCaseInput);
    ProfessionalInscriptionUseCase.Output expectedResponse = new ProfessionalInscriptionUseCase.Output(fakeProfessionalInscriptionResult());

    /**
     * then
     */
    Assertions.assertEquals(
            expectedResponse.getOutputBoundaryAdapter().getProfessionalId(),
            actualResponse.getOutputBoundaryAdapter().getProfessionalId()
    );

    Assertions.assertEquals(
            expectedResponse.getOutputBoundaryAdapter().getResponseMessage(),
            actualResponse.getOutputBoundaryAdapter().getResponseMessage()
    );
  }

  @Test
  public void ProfessionalInscriptionUseCase_should_throw_exception_because_email_is_already_register() {
    /**
     * given
     */
    when(clientInscriptionUseCase.execute(any())).thenThrow(ConflictException.class);
    IBoundaryInputAdapter professionalInscriptionInformationAdapter = fakeProfessionalInscriptionInformationAdapter();


    /**
     * when
     */
    ProfessionalInscriptionUseCase.Input professionalInscriptionUseCaseInput = new ProfessionalInscriptionUseCase.Input(professionalInscriptionInformationAdapter);
    Exception exception = Assertions.assertThrows(ConflictException.class, ()->professionalInscriptionUseCase.execute(professionalInscriptionUseCaseInput));

  }

  @Test
  public void should_add_new_professional() {
    /**
     * given
     */
    long clientId = 1l;
    when(professionalInscriptionRepository.addProfessional(any())).thenReturn(new IProfessionalInscription.ICreatedProfessional() {
      @Override
      public Long getProfessionalId() {
        return 1l;
      }
    });

    /**
     * when
     */
    long actualProfessionalId = professionalInscriptionUseCase.registerProfessional(clientId);
    long exptectedProfessionalId = 1l;

    /**
     * then
     */
    Assertions.assertEquals(exptectedProfessionalId, actualProfessionalId);
  }

  @Test
  public void should_create_professional_account() {
    /**
     * given
     */
    long professionalId = 1l;

    when(professionalInscriptionRepository.addProfessionalAccount(eq(professionalId), eq(false), any())).thenReturn(DataForTest.fakeProfessionalAccount());

    /**
     * when
     */
    long actualProfessionalAccoundId = professionalInscriptionUseCase.createProfessionalAccount(professionalId);
    long expetctedProfessionalAccoundId = 3L;

    /**
     * then
     */
    Assertions.assertEquals(expetctedProfessionalAccoundId, actualProfessionalAccoundId);
  }

}
