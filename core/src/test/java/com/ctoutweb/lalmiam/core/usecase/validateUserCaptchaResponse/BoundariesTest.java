package com.ctoutweb.lalmiam.core.usecase.validateUserCaptchaResponse;

import com.ctoutweb.lalamiam.core.entity.validateUserCaptchaResponse.impl.BoundaryInputImpl;
import com.ctoutweb.lalamiam.core.entity.validateUserCaptchaResponse.impl.BoundaryOutputImpl;
import com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryOutputAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import com.ctoutweb.lalamiam.core.useCase.impl.ValidateCaptchaResponseUseCase;

import static common.DataForTest.fakeUserCaptchaResponseAdapter;
import static org.mockito.ArgumentMatchers.any;

public class BoundariesTest {

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void should_validate_input() {
    /**
     * Given
     */



    ValidateCaptchaResponseUseCase.Input input = new ValidateCaptchaResponseUseCase.Input(fakeUserCaptchaResponseAdapter());

    /**
     * When
     */
    BoundaryInputImpl actualResponse = input.getBoundaryInput();
    BoundaryInputImpl expectedResponse =  new BoundaryInputImpl(
            fakeUserCaptchaResponseAdapter().getCaptchaResponseByUser(),
            fakeUserCaptchaResponseAdapter().getHashOrDecrypteCaptchaResponse(),
            fakeUserCaptchaResponseAdapter().getCryptographicType()
    );

    /**
     * Then
     */
    Assertions.assertEquals(expectedResponse.getCaptchaResponseByUser(), actualResponse.getCaptchaResponseByUser());
    Assertions.assertEquals(expectedResponse.cryptographicType(), actualResponse.cryptographicType());
    Assertions.assertEquals(expectedResponse.hashOrDecrypteCaptchaResponse(), actualResponse.hashOrDecrypteCaptchaResponse());
  }

  @Test
  public void should_validate_output_with_status_true() {
    /**
     * Given
     */
    BoundaryOutputImpl userCaptchaResponseResult = new BoundaryOutputImpl(
            true
    );
    ValidateCaptchaResponseUseCase.Output output = new ValidateCaptchaResponseUseCase.Output(userCaptchaResponseResult);

    /**
     * When
     */
    IBoundaryOutputAdapter actualResponse = output.getOutputBoundaryAdapter();
    IBoundaryOutputAdapter expectedResponse =  new IBoundaryOutputAdapter() {
      @Override
      public boolean getIsClientResponseValid() {
        return true;
      }
    };

    /**
     * Then
     */
    Assertions.assertEquals(expectedResponse.getIsClientResponseValid(), actualResponse.getIsClientResponseValid());
  }

  @Test
  public void should_validate_output_with_status_false() {
    /**
     * Given
     */
    BoundaryOutputImpl userCaptchaResponseResult = new BoundaryOutputImpl(
            false
    );
    ValidateCaptchaResponseUseCase.Output output = new ValidateCaptchaResponseUseCase.Output(userCaptchaResponseResult);

    /**
     * When
     */
    IBoundaryOutputAdapter actualResponse = output.getOutputBoundaryAdapter();
    IBoundaryOutputAdapter expectedResponse =  new IBoundaryOutputAdapter() {
      @Override
      public boolean getIsClientResponseValid() {
        return false;
      }
    };

    /**
     * Then
     */
    Assertions.assertEquals(expectedResponse.getIsClientResponseValid(), actualResponse.getIsClientResponseValid());
  }

  @Test
  public void should_not_validate_output() {
    /**
     * Given
     */
    BoundaryOutputImpl userCaptchaResponseResult = new BoundaryOutputImpl(
            true
    );
    ValidateCaptchaResponseUseCase.Output output = new ValidateCaptchaResponseUseCase.Output(userCaptchaResponseResult);

    /**
     * When
     */
    IBoundaryOutputAdapter actualResponse = output.getOutputBoundaryAdapter();
    IBoundaryOutputAdapter expectedResponse =  new IBoundaryOutputAdapter() {
      @Override
      public boolean getIsClientResponseValid() {
        return false;
      }
    };

    /**
     * Then
     */
    Assertions.assertNotEquals(expectedResponse.getIsClientResponseValid(), actualResponse.getIsClientResponseValid());
  }

}
