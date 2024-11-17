package usecase.ProfessionalInscription;

import adapter.professionalInscription.IBoundariesAdapter;
import common.DataForTest;
import entity.professionalInscription.impl.boundary.BoundaryInputImpl;
import entity.professionalInscription.impl.boundary.BoundaryOutputImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import useCase.impl.ProfessionalInscriptionUseCase;

public class BoundariesTest {

  @Test
  public void should_validate_input() {

    /**
     * given
     */
    IBoundariesAdapter.IBoundaryInputAdapter boundaryInputAdapter= DataForTest.fakeProfessionalInscriptionInformationAdapter();

    /**
     * when
     */
    ProfessionalInscriptionUseCase.Input professionalInscriptionInput = new ProfessionalInscriptionUseCase.Input(boundaryInputAdapter);

    BoundaryInputImpl actualResult = professionalInscriptionInput.getBoundaryInput();

    BoundaryInputImpl expectedResult = new BoundaryInputImpl(
            boundaryInputAdapter.getHashPassword(),
            boundaryInputAdapter.getEmail(),
            boundaryInputAdapter.getName(),
            boundaryInputAdapter.getFirstName(),
            boundaryInputAdapter.getPhone(),
            boundaryInputAdapter.getDocuments(),
            boundaryInputAdapter.getCaptchaResponseByUser(),
            boundaryInputAdapter.getHashOrDecrypteCaptchaResponse(),
            boundaryInputAdapter.getCryptographicType());

    /**
     * Then
     */
    Assertions.assertEquals(expectedResult, actualResult);
  }

  @Test
  public void should_validate_output() {

    /**
     * given
     */

    /**
     * when
     */
    ProfessionalInscriptionUseCase.Output output = new ProfessionalInscriptionUseCase.Output(new BoundaryOutputImpl(1L, "validation inscription"));
    IBoundariesAdapter.IBoundaryOutputAdapter actualResponse = output.getOutputBoundaryAdapter();
    IBoundariesAdapter.IBoundaryOutputAdapter expectedResponse = new BoundaryOutputImpl(1l, "validation inscription");
    /**
     * then
     */
    Assertions.assertEquals(expectedResponse, actualResponse);
  }
}
