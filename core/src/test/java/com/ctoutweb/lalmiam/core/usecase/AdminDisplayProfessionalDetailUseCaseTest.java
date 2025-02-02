package com.ctoutweb.lalmiam.core.usecase;

import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailInput;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IAdminDisplayProfessionalDetailRepository;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.adapter.IProfessionalDetail;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalDetail.useCase.AdminDisplayProfessionalDetailUseCase;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.ICoreMessageService;
import com.ctoutweb.lalamiam.core.useCase.base.adapter.ICoreEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.List;

public class AdminDisplayProfessionalDetailUseCaseTest {
  @Mock
  IAdminDisplayProfessionalDetailRepository adminDisplayProfessionalDetailRepository;
  @Mock
  ICoreMessageService messageService;
  @Mock
  ICoreEmailService emailService;

  AdminDisplayProfessionalDetailUseCase adminDisplayProfessionalDetailUseCase;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    adminDisplayProfessionalDetailUseCase = new AdminDisplayProfessionalDetailUseCase(
            adminDisplayProfessionalDetailRepository, messageService, emailService);
  }

  @Test
  public void execute_should_send_professionalDetail() {
    var input = AdminDisplayProfessionalDetailUseCase.Input.getInput(inputAdapter());

    /**
     * when-- profressional find and witing for confirmation
     */
    Mockito.when(adminDisplayProfessionalDetailRepository.findProfessionalDetail(Mockito.anyString())).thenReturn(getProfessionalDetail());
    var output = adminDisplayProfessionalDetailUseCase.execute(input);

    /**
     * then
     */
    var result = output.getOutputBoundary().getProfessionalDetail();
    Assertions.assertNotNull(result);
    Assertions.assertEquals("test@gmail", result.getProfessionalEmail());
    Assertions.assertEquals(1L, result.getProfessionalId());


  }

  @Test
  public void execute_should_throw_exception() {
    /**
     * Given
     */
    String error1 = "professional not find";
    String error2 = "professional account register not confirmed";
    String error3 = "professional account alreasy activated";

    var input = AdminDisplayProfessionalDetailUseCase.Input.getInput(inputAdapter());

    /**
     * Mock erreur
     */
    Mockito.when(messageService.getMessage("professional.not.find"))
            .thenReturn(error1);

    Mockito.when(messageService.getMessage("professional.register.account.not.confirm"))
            .thenReturn(error2);

    Mockito.when(messageService.getMessage("professional.account.already.activated"))
            .thenReturn(error3);

    /**
     * when-- profressional find and witing for confirmation
     */
    Mockito.when(adminDisplayProfessionalDetailRepository.findProfessionalDetail(Mockito.anyString()))
            .thenReturn(null)
            .thenReturn(getProfessionalDetailWithRegisterNotConfirmed())
            .thenReturn(getProfessionalDetailWithAccountAlreadyActivatedByAdmin());




    /**
     * When -- Professionul null
     */
    var result1 = Assertions.assertThrows(BadRequestException.class, ()->adminDisplayProfessionalDetailUseCase.execute(input));
    Assertions.assertEquals(error1, result1.getMessage());

    /**
     * When -- Professionul with account register not confirmed
     */
    var result2 = Assertions.assertThrows(BadRequestException.class, ()->adminDisplayProfessionalDetailUseCase.execute(input));
    Assertions.assertEquals(error2, result2.getMessage());

    /**
     * When -- Professionul with account already activated by admin
     */
    var result3 = Assertions.assertThrows(BadRequestException.class, ()->adminDisplayProfessionalDetailUseCase.execute(input));
    Assertions.assertEquals(error3, result3.getMessage());
  }

  private IAdminDisplayProfessionalDetailInput inputAdapter() {
    return new IAdminDisplayProfessionalDetailInput() {
      @Override
      public String getEmail() {
        return "test@gmail";
      }
    };
  }

  private IProfessionalDetail getProfessionalDetail() {
    return new IProfessionalDetail() {
      @Override
      public Long getProfessionalId() {
        return 1L;
      }

      @Override
      public String getProfessionalEmail() {
        return "test@gmail";
      }

      @Override
      public String getPhoneNumber() {
        return null;
      }

      @Override
      public String getFirstName() {
        return null;
      }

      @Override
      public String getLastName() {
        return null;
      }

      @Override
      public List<String> getStatutDocuments() {
        return null;
      }

      @Override
      public ZonedDateTime getAccountCreatedAt() {
        return null;
      }

      @Override
      public ZonedDateTime getAccountRegisterConfirmAt() {
        return null;
      }

      @Override
      public Boolean getIsAccountActivate() {
        return false;
      }

      @Override
      public Boolean getIsAccountRegisterConfirm() {
        return true;
      }
    };
  }

  private IProfessionalDetail getProfessionalDetailWithRegisterNotConfirmed() {
    return new IProfessionalDetail() {
      @Override
      public Long getProfessionalId() {
        return 1L;
      }

      @Override
      public String getProfessionalEmail() {
        return "test@gmail";
      }

      @Override
      public String getPhoneNumber() {
        return null;
      }

      @Override
      public String getFirstName() {
        return null;
      }

      @Override
      public String getLastName() {
        return null;
      }

      @Override
      public List<String> getStatutDocuments() {
        return null;
      }

      @Override
      public ZonedDateTime getAccountCreatedAt() {
        return null;
      }

      @Override
      public ZonedDateTime getAccountRegisterConfirmAt() {
        return null;
      }

      @Override
      public Boolean getIsAccountActivate() {
        return false;
      }

      @Override
      public Boolean getIsAccountRegisterConfirm() {
        return false;
      }
    };
  }

  private IProfessionalDetail getProfessionalDetailWithAccountAlreadyActivatedByAdmin() {
    return new IProfessionalDetail() {
      @Override
      public Long getProfessionalId() {
        return 1L;
      }

      @Override
      public String getProfessionalEmail() {
        return "test@gmail";
      }

      @Override
      public String getPhoneNumber() {
        return null;
      }

      @Override
      public String getFirstName() {
        return null;
      }

      @Override
      public String getLastName() {
        return null;
      }

      @Override
      public List<String> getStatutDocuments() {
        return null;
      }

      @Override
      public ZonedDateTime getAccountCreatedAt() {
        return null;
      }

      @Override
      public ZonedDateTime getAccountRegisterConfirmAt() {
        return null;
      }

      @Override
      public Boolean getIsAccountActivate() {
        return true;
      }

      @Override
      public Boolean getIsAccountRegisterConfirm() {
        return true;
      }
    };
  }

}
