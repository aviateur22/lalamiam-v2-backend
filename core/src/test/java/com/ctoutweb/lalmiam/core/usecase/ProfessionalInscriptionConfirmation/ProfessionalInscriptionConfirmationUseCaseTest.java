package com.ctoutweb.lalmiam.core.usecase.ProfessionalInscriptionConfirmation;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalAccountInformation;
import com.ctoutweb.lalamiam.core.useCase.base.gateway.IProfessionalInformation;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.port.IProfessionalInscriptionConfirmationInput;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.entity.IProfessionalInscriptionConfirmation;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.useCase.ProfessionalInscriptionConfirmationUseCase;
import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import org.mockito.Mockito;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.useCase.professionalInscriptionConfirmation.gateway.IProfessionalInscriptionConfirmationRepository;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ProfessionalInscriptionConfirmationUseCaseTest {
  @Mock
  IMessageService messageService;
  @Mock
  IProfessionalInscriptionConfirmationRepository professionalInscriptionConfirmationRepository;
  ProfessionalInscriptionConfirmationUseCase professionalInscriptionConfirmationUseCase;

  private IProfessionalInscriptionConfirmation professionalInscriptionConfirmation;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);

    professionalInscriptionConfirmationUseCase = new ProfessionalInscriptionConfirmationUseCase(
            professionalInscriptionConfirmationRepository,
            messageService);
  }

  @Test
  public void ProfessionalInscriptionConfirmationUseCase_should_success_professional_inscription() {
    /**
     * given
     */
    ZonedDateTime fakeConfirmAccountRegisterLimitDate = ZonedDateTime.of(LocalDateTime.now().plusDays(2), ZoneId.of("Europe/Paris"));
    IProfessionalInformation professionalInformation = getProfessionalInformation(fakeConfirmAccountRegisterLimitDate);
    String expectedResponse = "Confirmation enregistrement";
    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(Mockito.any())).thenReturn(professionalInformation);
    Mockito.when(messageService.getMessage("professional.register.confirmation")).thenReturn(expectedResponse);
    /**
     * when
     */
    ProfessionalInscriptionConfirmationUseCase.Input input = new ProfessionalInscriptionConfirmationUseCase.Input(getProfessionalConfirmationInput());
    ProfessionalInscriptionConfirmationUseCase.Output output = professionalInscriptionConfirmationUseCase.execute(input);
    String actualResponse = output.getOutputBoundary().getResponseMessage();

    /**
     * then
     */
    Assertions.assertEquals(expectedResponse, actualResponse);
  }

  @Test
  public void ProfessionalInscriptionConfirmationUseCase_should_failed_professional_inscription() {
    /**
     * given
     */
    ZonedDateTime fakeConfirmAccountRegisterLimitDate = ZonedDateTime.of(LocalDateTime.now().plusDays(-2), ZoneId.of("Europe/Paris"));
    ZonedDateTime fakeConfirmAccountRegisterLimitDate2 = ZonedDateTime.of(LocalDateTime.now().plusDays(2), ZoneId.of("Europe/Paris"));

    // Cas avec une date de confirmation inscription dépassée
    IProfessionalInformation professionalInformation = getProfessionalInformation(fakeConfirmAccountRegisterLimitDate);

    // Cas d'un enregistrement déja effectué
    IProfessionalInformation professionalInformation2 = getProfessionalInformation2(fakeConfirmAccountRegisterLimitDate2);

    Mockito.when(professionalInscriptionConfirmationRepository.findProfessionalByEmail(Mockito.any()))
            .thenReturn(professionalInformation)
            .thenReturn(professionalInformation2);

    /**
     * when -- date de confirmation dépassé
     */
    String expectedErrorMessage = "Date de confirmation dépasser";
    Mockito.when(messageService.getMessage("professional.account.limit.date.expired"))
            .thenReturn(expectedErrorMessage);

    ProfessionalInscriptionConfirmationUseCase.Input input = new ProfessionalInscriptionConfirmationUseCase.Input(getProfessionalConfirmationInput());
    Exception exception = Assertions.assertThrows(BadRequestException.class, ()-> professionalInscriptionConfirmationUseCase.execute(input));

    /**
     * When -- Inscription déja confirmé
     */
    String expectedErrorMessage2 = "Inscription déja confirmée";
     Mockito.when(messageService.getMessage("professional.account.already.confirmed")).thenReturn(expectedErrorMessage2);

    ProfessionalInscriptionConfirmationUseCase.Input input2 = new ProfessionalInscriptionConfirmationUseCase.Input(getProfessionalConfirmationInput());
    Exception exception2 = Assertions.assertThrows(BadRequestException.class, ()-> professionalInscriptionConfirmationUseCase.execute(input2));

    /**
     * then -- date de confirmation dépassé
     */
      Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

    /**
     * then -- Inscription déja confirmé
     */
    Assertions.assertEquals(expectedErrorMessage2, exception2.getMessage());
  }

  public IProfessionalInscriptionConfirmationInput getProfessionalConfirmationInput() {
    return new IProfessionalInscriptionConfirmationInput() {
      @Override
      public String getProfessionalEmail() {
        return "email@gmail.com";
      }
    };
  }

  /**
   * Données si professionel n' a pas encore confirmé son inscription
   * @param fakeConfirmAccountRegisterLimitDate
   * @return
   */
  public IProfessionalInformation getProfessionalInformation(ZonedDateTime fakeConfirmAccountRegisterLimitDate) {
return new IProfessionalInformation() {
  @Override
  public Long getProfessionalId() {
    return 1L;
  }

  @Override
  public String getEmail() {
    return "test@gmail.com";
  }

  @Override
  public IProfessionalAccountInformation getProfessionalAccountInformation() {
    return new IProfessionalAccountInformation() {
      @Override
      public Long getAccountProfessionalId() {
        return 1L;
      }

      @Override
      public ZonedDateTime getAccountRegisterConfirmationLimitTime() {
        return fakeConfirmAccountRegisterLimitDate;
      }

      @Override
      public Boolean getIsAccountActivated() {
        return false;
      }

      @Override
      public Boolean getIsRegisterConfirmByProfessional() {
        return false;
      }
    };
  }
  };
}

  /**
   * Données si professionel a déja confirmé son inscription
   * @param fakeConfirmAccountRegisterLimitDate
   * @return
   */
  public IProfessionalInformation getProfessionalInformation2(ZonedDateTime fakeConfirmAccountRegisterLimitDate) {

    return new IProfessionalInformation() {
      @Override
      public Long getProfessionalId() {
        return 1L;
      }

      @Override
      public String getEmail() {
        return "email@gmail.com";
      }

      @Override
      public IProfessionalAccountInformation getProfessionalAccountInformation() {
        return new IProfessionalAccountInformation() {
          @Override
          public Long getAccountProfessionalId() {
            return 1L;
          }

          @Override
          public ZonedDateTime getAccountRegisterConfirmationLimitTime() {
            return fakeConfirmAccountRegisterLimitDate;
          }

          @Override
          public Boolean getIsAccountActivated() {
            return false;
          }

          @Override
          public Boolean getIsRegisterConfirmByProfessional() {
            return true;
          }
        };
      }
    };
  }

}
