//package com.ctoutweb.lalmiam.core.usecase.ProfessionalInscription;
//
//import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter;
//import common.DataForTest;
//import com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary.BoundaryInputImpl;
//import com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary.BoundaryOutputImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import com.ctoutweb.lalamiam.core.useCase.impl.ProfessionalInscriptionUseCase;
//
//public class BoundariesTest {
//
//  @Test
//  public void should_validate_input() {
//
//    /**
//     * given
//     */
//    IBoundariesAdapter.IBoundaryInputAdapter boundaryInputAdapter= DataForTest.fakeProfessionalInscriptionInformationAdapter();
//
//    /**
//     * when
//     */
//    ProfessionalInscriptionUseCase.Input professionalInscriptionInput = new ProfessionalInscriptionUseCase.Input(boundaryInputAdapter);
//
//    BoundaryInputImpl actualResult = professionalInscriptionInput.getBoundaryInput();
//
//    BoundaryInputImpl expectedResult = new BoundaryInputImpl(
//            boundaryInputAdapter.getHashPassword(),
//            boundaryInputAdapter.getEmail(),
//            boundaryInputAdapter.getNickName(),
//            boundaryInputAdapter.getLastName(),
//            boundaryInputAdapter.getFirstName(),
//            boundaryInputAdapter.getPhone(),
//            boundaryInputAdapter.getDocuments()
//    );
//
//    /**
//     * Then
//     */
//    Assertions.assertEquals(expectedResult, actualResult);
//  }
//
//  @Test
//  public void should_validate_output() {
//
//    /**
//     * given
//     */
//
//    /**
//     * when
//     */
//    ProfessionalInscriptionUseCase.Output output = new ProfessionalInscriptionUseCase.Output(new BoundaryOutputImpl(1L, "validation inscription"));
//    IBoundariesAdapter.IBoundaryOutputAdapter actualResponse = output.getOutputBoundaryAdapter();
//    IBoundariesAdapter.IBoundaryOutputAdapter expectedResponse = new BoundaryOutputImpl(1l, "validation inscription");
//    /**
//     * then
//     */
//    Assertions.assertEquals(expectedResponse, actualResponse);
//  }
//}
