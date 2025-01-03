package com.ctoutweb.lalmiam.core.usecase.clientInscription;

import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryOutputAdapter;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription;
import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.entity.CreatedAccountImpl;
import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.entity.CreatedClientImpl;
import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryInputImpl;
import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryOutputImpl;
import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.core.provider.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.provider.INotificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.useCase.impl.ValidateCaptchaResponseUseCase;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;

import static common.DataForTest.fakeClientInscriptionAdapter;
import static org.mockito.ArgumentMatchers.*;

public class ClientInscriptionUseCaseTest {
  ClientInscriptionUseCase clientInscriptionUseCase;

  @Mock
  IClientInscriptionRepository clientInscriptionRepository;

  @Mock
  IMessageService messageService;

  @Mock
  INotificationService notificationService;
  @BeforeEach
  void init() {

    MockitoAnnotations.openMocks(this);

    clientInscriptionUseCase = new ClientInscriptionUseCase(
            messageService,
            notificationService,
            clientInscriptionRepository
    );
  }

  @Test
  public void clientInscriptionUseCase_should_register_user() {
    /**
     * Given
     */
    IBoundaryInputAdapter clientInscriptionAdapter = fakeClientInscriptionAdapter();
    Mockito.when(messageService.getMessage("register.success")).thenReturn("Bonjour");
    Mockito.when(clientInscriptionRepository.findUserByEmail(clientInscriptionAdapter.getEmail())).thenReturn(null);
    Mockito.when(clientInscriptionRepository.addClient(clientInscriptionAdapter.getEmail(), clientInscriptionAdapter.getHashPassword())).thenReturn(new CreatedClientImpl(1l));
    Mockito.when(clientInscriptionRepository.createAccountClient(1l)).thenReturn(new CreatedAccountImpl(1l));

    // Données a injecter dans le useCase
    var input = ClientInscriptionUseCase.Input.getUseCaseInput(clientInscriptionAdapter);
//
//    // initialisation mapper validateUserCaptchaResponseMapper
//    IMapper<ValidateUserCaptchaResponseAdapter, ValidateUserCaptchaResponse> validateUserCaptchaResponseMapper = new UserCaptchachResponseMapper(captchaService);
//
//    // Donnéd a injecté dans le useCase CaptchaValidateClientResponseUseCase
//    CaptchaValidateClientResponseUseCase.Input captchaInput = new CaptchaValidateClientResponseUseCase.Input(clientInscriptionAdapter, validateUserCaptchaResponseMapper);


    /**
     * When
     */
    ClientInscriptionUseCase.Output actualOutput = clientInscriptionUseCase.execute(input);
    ClientInscriptionUseCase.Output expectedOutput = ClientInscriptionUseCase.Output.getUseCaseOutput(new BoundaryOutputImpl("", 1l));

    // Reponse attendu
    IBoundaryOutputAdapter expectedResponse = new BoundaryOutputImpl("Bonjour", 1L);

    /**
     * then
     */
    Assertions.assertEquals(expectedResponse.getUserId(),actualOutput.getOutputBoundaryAdapter().getUserId());
    Assertions.assertEquals(expectedResponse.getResponseMessage(),actualOutput.getOutputBoundaryAdapter().getResponseMessage());
  }

  @Test
  public void clientInscriptionUseCase_execute_captchaValidateClientResponseUseCase_should_failed_capatcha_result() {
    IBoundaryInputAdapter clientInscriptionAdapter = fakeClientInscriptionAdapter();

    ClientInscriptionUseCase clientInscriptionUseCase = new ClientInscriptionUseCase(
            messageService,
            notificationService,
            clientInscriptionRepository
    );

    ValidateCaptchaResponseUseCase.Output output = clientInscriptionUseCase.executeCaptchaValidateClientResponseUseCase(clientInscriptionAdapter);
  }

  @Test
  public void user_email_should_be_avail() {

    /**
     * given
     */
    IBoundaryInputAdapter fakeClientInscriptionAdapter = fakeClientInscriptionAdapter();
    Mockito.when(clientInscriptionRepository.findUserByEmail(anyString())).thenReturn(null);

    /**
     * when
     */

    boolean actualResponse = clientInscriptionUseCase.isEmailAvailable(fakeClientInscriptionAdapter.getEmail());

    /**
     * then
     */
    Assertions.assertTrue(actualResponse);

  }

  @Test
  public void user_email_should_not_be_avail() {
    /**
     * Given
     */
    Mockito.when(clientInscriptionRepository.findUserByEmail(any())).thenReturn(null);

    IBoundaryInputAdapter clientInscriptionAdapter = fakeClientInscriptionAdapter();

    var clientInscriptionInput = ClientInscriptionUseCase.Input.getUseCaseInput(clientInscriptionAdapter);

    var clientInscriptionInformation = BoundaryInputImpl.getBoundaryInputImpl(
            clientInscriptionAdapter.getHashPassword(),
            clientInscriptionAdapter.getEmail(),
            clientInscriptionAdapter.getNickName()

    );

    /**
     * When
     */
    boolean actualEmailClientAvailAvailibility = clientInscriptionUseCase.isEmailAvailable(clientInscriptionInformation.getEmail());
    boolean expectedEmailClientAvailibility = true;

    /**
     * Then
     */
    Assertions.assertEquals(expectedEmailClientAvailibility, actualEmailClientAvailAvailibility);
  }

  @Test
  public void ClientInscriptionUsecase_should_throw_exception_when_user_email_not_avail() {
    /**
     * Given
     */
    Mockito.when(clientInscriptionRepository.findUserByEmail(any())).thenReturn((new CreatedClientImpl(1l)));

    IBoundaryInputAdapter clientInscriptionAdapter = fakeClientInscriptionAdapter();

    /**
     * Wen
     */
    var clientInscriptionUseCaseInput = ClientInscriptionUseCase.Input.getUseCaseInput(clientInscriptionAdapter);
    Exception exception = Assertions.assertThrows(ConflictException.class, ()->clientInscriptionUseCase.execute(clientInscriptionUseCaseInput));


  }

  @Test
  public void should_persist_client_and_role_of_client() {
    /**
     * given
     */
    IBoundaryInputAdapter clientInscriptionInformationAdapter = fakeClientInscriptionAdapter();
    var clientInscriptionUseCaseInput = ClientInscriptionUseCase.Input.getUseCaseInput(clientInscriptionInformationAdapter);

    var clientInscriptionInformation = BoundaryInputImpl.getBoundaryInputImpl(
            clientInscriptionInformationAdapter.getHashPassword(),
            clientInscriptionInformationAdapter.getEmail(),
            clientInscriptionInformationAdapter.getNickName()
    );

    Mockito.when(clientInscriptionRepository.addClient(clientInscriptionInformation.getEmail(), clientInscriptionInformation.getHashPassword())).thenReturn(new CreatedClientImpl(1l));
    Mockito.when(clientInscriptionRepository.createRoleClient(anyLong(), anyInt())).thenReturn(new IClientInscription.ICreatedRole() {
      @Override
      public Long getClientRole() {
        return null;
      }

      @Override
      public Integer getRoleId() {
        return null;
      }
    });

    /**
     * when
     */
    long actualClientId = clientInscriptionUseCase.createClient(clientInscriptionInformation);
    long expectedClientId = 1L;

    /**
     * then
     */
      Assertions.assertEquals(expectedClientId, actualClientId);
  }

  @Test
  public void should_create_account_for_client() {
    /**
     * given
     */
    IBoundaryInputAdapter clientInscriptionInformationAdapter = fakeClientInscriptionAdapter();

    var clientInscriptionUseCaseInput = ClientInscriptionUseCase.Input.getUseCaseInput(clientInscriptionInformationAdapter);

    var clientInscriptionInformation = BoundaryInputImpl.getBoundaryInputImpl(
            clientInscriptionInformationAdapter.getHashPassword(),
            clientInscriptionInformationAdapter.getEmail(),
            clientInscriptionInformationAdapter.getNickName()
    );

    Mockito.when(clientInscriptionRepository.createAccountClient(1l)).thenReturn(new CreatedAccountImpl(1l));


    /**
     * when
     */
    long actualAccountId = clientInscriptionUseCase.createClientAccount(1l);
    long expectedAccountId = 1L;

    /**
     * then
     */
    Assertions.assertEquals(expectedAccountId, actualAccountId);
  }




}
