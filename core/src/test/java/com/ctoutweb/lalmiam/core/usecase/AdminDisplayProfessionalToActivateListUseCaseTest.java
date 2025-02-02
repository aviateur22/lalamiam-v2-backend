package com.ctoutweb.lalmiam.core.usecase;

import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivate;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.adapter.IProfessionalToActivateRepository;
import com.ctoutweb.lalamiam.core.useCase.adminDisplayProfessionalToActivateList.useCase.AdminDisplayProfessionalToActivateListUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.List;

public class AdminDisplayProfessionalToActivateListUseCaseTest {

  @Mock
  IProfessionalToActivateRepository professionalToActivateRepository;
  AdminDisplayProfessionalToActivateListUseCase adminDisplayProfessionalToActivateListUseCase;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    adminDisplayProfessionalToActivateListUseCase = new AdminDisplayProfessionalToActivateListUseCase(professionalToActivateRepository);
  }

  @Test
  public void execute_should_get_list_of_professional_to_activate() {
    /**
     * Given
     */
    // Input non requis pour le usecase
    AdminDisplayProfessionalToActivateListUseCase.Input input = null;

    /**
     * When
     */

    Mockito.when(professionalToActivateRepository.getProfessionalToActivateList())
            .thenReturn(List.of())
            .thenReturn(getProfessionals());

    /**
     * Execute -- Liste vide
     */
    AdminDisplayProfessionalToActivateListUseCase.Output result1 = adminDisplayProfessionalToActivateListUseCase.execute(input);

    /**
     * Execute -- Liste avec professionel
     */
    AdminDisplayProfessionalToActivateListUseCase.Output result2 = adminDisplayProfessionalToActivateListUseCase.execute(input);

    /**
     * Then -- Liste vide
     */

    Assertions.assertEquals(List.of(), result1.getOutputBoundary().getProfessionalToActivateList());
    Assertions.assertEquals(2, result2.getOutputBoundary().getProfessionalToActivateList().size());





  }

  public List<IProfessionalToActivate> getProfessionals() {
    IProfessionalToActivate professional1 = new IProfessionalToActivate() {
      @Override
      public Long getProfessionalId() {
        return 1L;
      }

      @Override
      public String getProfessionalEmail() {
        return "test@gmail.com";
      }

      @Override
      public ZonedDateTime getAccountCreatedAt() {
        return ZonedDateTime.now();
      }
    };

    IProfessionalToActivate professional2 = new IProfessionalToActivate() {
      @Override
      public Long getProfessionalId() {
        return 2L;
      }

      @Override
      public String getProfessionalEmail() {
        return "test2@gmail.com";
      }

      @Override
      public ZonedDateTime getAccountCreatedAt() {
        return ZonedDateTime.now();
      }
    };
    return List.of(professional1, professional2);
  }
}
