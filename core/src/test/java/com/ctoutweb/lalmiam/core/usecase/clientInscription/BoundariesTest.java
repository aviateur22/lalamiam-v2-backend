package com.ctoutweb.lalmiam.core.usecase.clientInscription;

import com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries.BoundaryInputImpl;
import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryOutputAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.ctoutweb.lalamiam.core.provider.IClientInscriptionRepository;
import com.ctoutweb.lalamiam.core.useCase.impl.ClientInscriptionUseCase;

import static common.DataForTest.*;

public class BoundariesTest {
  @Mock
  IClientInscriptionRepository userInscriptionRepository;

  @Test
  public void should_validate_input() {
    /**
     * Doit convertir un Adapter vers une Implémentation concréte dans le useCase
     */

    /**
     * Given
     */
    IBoundaryInputAdapter userInscriptionAdapter = fakeClientInscriptionAdapter();

    /**
     * When
     */
    ClientInscriptionUseCase.Input input = new ClientInscriptionUseCase.Input(userInscriptionAdapter);
    BoundaryInputImpl actualResponse = input.getBoundaryInput();

    BoundaryInputImpl expectedResult = new BoundaryInputImpl(
            userInscriptionAdapter.getHashPassword(),
            userInscriptionAdapter.getEmail(),
            userInscriptionAdapter.getNickName()
    );

    /**
     * Then
     */
    Assertions.assertEquals(expectedResult.getNickName(), actualResponse.getNickName());
    Assertions.assertEquals(expectedResult.getEmail(), actualResponse.getEmail());
    Assertions.assertEquals(expectedResult.getHashPassword(), actualResponse.getHashPassword());
  }

  @Test
  public void should_validate_output() {
    /**
     * Doit convertir une implémentation concrète vers un Adapter pour la sortie du useCase
     */

    ClientInscriptionUseCase.Output actualResponse = new ClientInscriptionUseCase.Output(userInscriptionResponse());

    IBoundaryOutputAdapter expectedResponse = new IBoundaryOutputAdapter() {
      @Override
      public Long getUserId() {
        return userInscriptionResponseAdapter().getUserId();
      }

      @Override
      public String getResponseMessage() {
        return userInscriptionResponseAdapter().getResponseMessage();
      }
    };

    Assertions.assertEquals(expectedResponse.getUserId(), actualResponse.getOutputBoundaryAdapter().getUserId());
    Assertions.assertEquals(expectedResponse.getResponseMessage(), actualResponse.getOutputBoundaryAdapter().getResponseMessage());
  }
}
