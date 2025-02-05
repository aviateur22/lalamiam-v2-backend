package com.ctoutweb.lalmiam.core.usecase;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.port.IClientInscriptionOutput;
import com.ctoutweb.lalamiam.core.useCase.clientInscription.useCase.ClientInscriptionUseCase;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessional;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ICreatedProfessionalAccount;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.IProfessionalInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.gateway.ISavedInscriptionDocuments;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.useCase.ProfessionalInscriptionUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ProfessionalInscriptionUseCaseTest {
  @Mock
  ClientInscriptionUseCase clientInscriptionUseCase;
  @Mock
  ICoreMessageService messageService;
  @Mock
  IProfessionalInscriptionRepository professionalInscriptionRepository;
  @Mock
  ICoreEmailService coreEmailService;
  ProfessionalInscriptionUseCase professionalInscriptionUseCase;

  @BeforeEach
  public void init()  {
    MockitoAnnotations.openMocks(this);

    professionalInscriptionUseCase = new ProfessionalInscriptionUseCase(
            clientInscriptionUseCase, messageService, professionalInscriptionRepository, coreEmailService);
  }
  @Test
  public void execute() {
    /**
     *
     */
    when(clientInscriptionUseCase.execute(any())).thenReturn(new ClientInscriptionUseCase.Output(getClientInscriptionOutput()));
    when(professionalInscriptionRepository.addProfessional(eq(1L), any())).thenReturn(getCreatedProfessional());
    when(professionalInscriptionRepository.saveProfessionalInscriptionDocument(any(), eq(1L))).thenReturn(getSavedDocuements());
    when(professionalInscriptionRepository.addProfessionalAccount(1L)).thenReturn(getCreatedAccount());
    when(messageService.getMessage("register.success")).thenReturn("welcome");

    var input = ProfessionalInscriptionUseCase.Input.getInput(getProfessionalInformation());

    /**
     * when
     */
    ProfessionalInscriptionUseCase.Output output = professionalInscriptionUseCase.execute(input);

    /**
     * then
     */
    var result = output.getOutputBoundary();
    Assertions.assertEquals(1L, result.getProfessionalId());
    Assertions.assertEquals(1L, result.getProfessionalAccountId());
    Assertions.assertEquals(List.of(1L, 2L), result.getInscriptionDocumentsIds());
    Assertions.assertEquals("welcome", result.getResponseMessage());
  }

  private IClientInscriptionOutput getClientInscriptionOutput() {
    return new IClientInscriptionOutput() {
      @Override
      public Long getUserId() {
        return 1L;
      }

      @Override
      public Long getUserAccountId() {
        return 1L;
      }
    };
  }
  private IProfessionalInscriptionInput getProfessionalInformation() {
    return new IProfessionalInscriptionInput() {
      @Override
      public String getLastName() {
        return "null";
      }

      @Override
      public String getFirstName() {
        return "null";
      }

      @Override
      public String getPhone() {
        return null;
      }

      @Override
      public List<File> getProfessionalInscriptionDocuments() {
        return null;
      }

      @Override
      public String getHashPassword() {
        return null;
      }

      @Override
      public String getEmail() {
        return null;
      }

      @Override
      public String getNickName() {
        return null;
      }
    };
  }
  private ICreatedProfessional getCreatedProfessional() {
    return new ICreatedProfessional() {
      @Override
      public Long getProfessionalId() {
        return 1L;
      }
    };
  }
  private ISavedInscriptionDocuments getSavedDocuements() {
    return new ISavedInscriptionDocuments() {
      @Override
      public List<Long> getInscriptionDocumentId() {
        return List.of(1L, 2L);
      }
    };
  }
  private ICreatedProfessionalAccount getCreatedAccount() {
    return new ICreatedProfessionalAccount() {
      @Override
      public Long getAccountId() {
        return 1L;
      }
    };
  }
}
