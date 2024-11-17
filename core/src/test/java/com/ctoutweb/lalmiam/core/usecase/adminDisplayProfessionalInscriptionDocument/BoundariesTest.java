package com.ctoutweb.lalmiam.core.usecase.adminDisplayProfessionalInscriptionDocument;

import com.ctoutweb.lalamiam.core.adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.core.adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryOutputAdapter;
import common.DataForTest;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.impl.boundaries.BoundaryInputImpl;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.impl.boundaries.BoundaryOutputImpl;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.impl.entity.ProfessionalInscriptionDocumentImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.ctoutweb.lalamiam.core.useCase.impl.AdminDisplayProfessionalInscriptionDocumentUseCase;

import java.io.File;

public class BoundariesTest {

  AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase;


  @Test
  public void should_validate_input() {
    /**
     * given
     */
    IBoundaryInputAdapter boundaryInputAdapter = new IBoundaryInputAdapter() {
      @Override
      public Long getProfessionalId() {
        return 1l;
      }
    };

    /**
     * when
     */
    var input = AdminDisplayProfessionalInscriptionDocumentUseCase.Input.getUseCaseInput(boundaryInputAdapter);
    BoundaryInputImpl actualResponse = input.getBoundaryInput();

    /**
     * then
     */
    Assertions.assertEquals(boundaryInputAdapter.getProfessionalId(), actualResponse.getProfessionalId());

  }

  @Test
  public void should_validate_output() {
    /**
     * given
     */
    ProfessionalInscriptionDocumentImpl professionalInscriptionDocument = DataForTest.fakeProfessionalInscriptionDocumentImpl();
    BoundaryOutputImpl boundaryOutput = BoundaryOutputImpl.getBoundaryOutput(professionalInscriptionDocument);

    /**
     * when
     */
    var output = AdminDisplayProfessionalInscriptionDocumentUseCase.Output.getUseCaseOutput(boundaryOutput);
    IBoundaryOutputAdapter actualResponse = output.getOutputBoundaryAdapter();
    IBoundaryOutputAdapter expectedResponse = new IBoundaryOutputAdapter() {
      @Override
      public Long getProfessionalId() {
        return professionalInscriptionDocument.getProfessionalId();
      }

      @Override
      public String getProfessionalEmail() {
        return professionalInscriptionDocument.getProfessionalEmail();
      }

      @Override
      public String getProfessionalPhone() {
        return professionalInscriptionDocument.getProfessionalPhone();
      }

      @Override
      public File getProfessionalFileStatus() {
        return professionalInscriptionDocument.getProfessionalFileStatus();
      }
    };

    /**
     * then
     */
    Assertions.assertEquals(expectedResponse.getProfessionalFileStatus(), actualResponse.getProfessionalFileStatus());
    Assertions.assertEquals(expectedResponse.getProfessionalEmail(), actualResponse.getProfessionalEmail());
    Assertions.assertEquals(expectedResponse.getProfessionalId(), actualResponse.getProfessionalId());
    Assertions.assertEquals(expectedResponse.getProfessionalPhone(), actualResponse.getProfessionalPhone());

  }
}
