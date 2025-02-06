package com.ctoutweb.lalmiam.core.usecase;

import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedAccount;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.gateway.ICreatedClient;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.impl.ClientInscriptionInputImpl;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase.ClientInscriptionUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ClientInscriptionUseCaseTest {
  @Mock
  IClientInscriptionRepository clientInscriptionRepository;

  @Mock
  ICoreMessageService messageService;

  @Mock
  ICoreEmailService emailService;
  ClientInscriptionUseCase clientInscriptionUseCase;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    clientInscriptionUseCase = new ClientInscriptionUseCase(clientInscriptionRepository, emailService, messageService);
  }
  @Test
  public void execute_should_rigster_user() {
    /**
     * Given
     */

    ClientInscriptionUseCase.Input input = ClientInscriptionUseCase.Input.getInput(getClientInscriptionInput());
    when(clientInscriptionRepository.isEmailAvailable(anyString())).thenReturn(true);
    when(clientInscriptionRepository.addClient(anyString(), anyString(), anyString())).thenReturn(getCreatedClient());
    when(clientInscriptionRepository.createAccountClient(anyLong())).thenReturn(getCreatedAccount());
    when(messageService.getMessage("register.success")).thenReturn("success");

    /**
     * when
     */
    ClientInscriptionUseCase.Output output = clientInscriptionUseCase.execute(input);
    var result = output.getOutputBoundary();

    /**
     * then
     */
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1L, result.getUserAccountId());
    Assertions.assertEquals(1L, result.getUserId());
    Assertions.assertEquals("success", result.getResponseMessage());
  }
  @Test
  public void execute_should_throw_exception() {
    /**
     * Given
     */

    ClientInscriptionUseCase.Input input = ClientInscriptionUseCase.Input.getInput(getClientInscriptionInput());
    when(clientInscriptionRepository.isEmailAvailable(anyString())).thenReturn(false);
    when(messageService.getMessage("email.exist")).thenReturn("email exist");

    /**
     * when
     */
    Exception exception = Assertions.assertThrows(ConflictException.class, ()->clientInscriptionUseCase.execute(input));

    /**
     * then
     */
    Assertions.assertEquals("email exist", exception.getMessage());
  }
  private final IClientInscriptionInput getClientInscriptionInput() {
    return new ClientInscriptionInputImpl("mypassword", "email", "nickname");
  }

  private final ICreatedClient getCreatedClient() {
    return new ICreatedClient() {
      @Override
      public Long getClientId() {
        return 1L;
      }

      @Override
      public Long getRoleId() {
        return 1L;
      }
    };
  }

  private final ICreatedAccount getCreatedAccount() {
    return new ICreatedAccount() {
      @Override
      public Long getAccountId() {
        return 1L;
      }
    };
  }

}
