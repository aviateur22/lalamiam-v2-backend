//package com.ctoutweb.lalmiam.core.usecase.ProfessionalInscriptionConfirmation;
//
//import com.ctoutweb.lalamiam.core.adapter.professionalInscriptionConfirmation.IBoundariesAdapter;
//import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.impl.boundary.BoundaryInputImpl;
//import com.ctoutweb.lalamiam.core.entity.professionalInscriptionConfirmation.impl.boundary.BoundaryOutputImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import com.ctoutweb.lalamiam.core.useCase.impl.ProfessionalInscriptionConfirmationUseCase;
//
//public class BoundariesTest {
//  @Test
//  public void should_validate_input() {
//    /**
//     * given
//     */
//    IBoundariesAdapter.IBoundaryInputAdapter professionalConfirmationInformationAdapter = new IBoundariesAdapter.IBoundaryInputAdapter() {
//      @Override
//      public String getProfessionalEmailToken() {
//        return "professionalemailtoken";
//      }
//
//      @Override
//      public String getProfessionalUrlToken() {
//        return "professionalurltoken";
//      }
//
//      @Override
//      public String getProfessionalEmail() {
//        return "professionalemail@gmail.com";
//      }
//    };
//
//    /**
//     * when
//     */
//    ProfessionalInscriptionConfirmationUseCase.Input input = ProfessionalInscriptionConfirmationUseCase
//            .Input
//            .getUseCaseInput(professionalConfirmationInformationAdapter);
//
//    BoundaryInputImpl actualResponse = input.getBoundaryInput();
//    BoundaryInputImpl expectedResponse = new BoundaryInputImpl(
//            professionalConfirmationInformationAdapter.getProfessionalEmail(),
//            professionalConfirmationInformationAdapter.getProfessionalEmailToken(),
//            professionalConfirmationInformationAdapter.getProfessionalUrlToken()
//    );
//
//    /**
//     *
//     */
//    Assertions.assertEquals(expectedResponse.getProfessionalEmail(), actualResponse.getProfessionalEmail());
//  }
//
//  @Test
//  public void should_validate_output() {
//
//    /**
//     * given
//     */
//    boolean isInscriptionConfirm = true;
//    String outputMessage = "Fake message";
//    BoundaryOutputImpl boundaryOutput = new BoundaryOutputImpl(isInscriptionConfirm, outputMessage);
//
//    ProfessionalInscriptionConfirmationUseCase.Output output = new ProfessionalInscriptionConfirmationUseCase.Output(boundaryOutput);
//    IBoundariesAdapter.IBoundaryOutputAdapter actualResponse = output.getOutputBoundaryAdapter();
//
//
//    /**
//     *
//     */
//    Assertions.assertTrue(actualResponse.isConfirmationValid());
//    Assertions.assertEquals(outputMessage, actualResponse.getResponseMessage());
//  }
//}
