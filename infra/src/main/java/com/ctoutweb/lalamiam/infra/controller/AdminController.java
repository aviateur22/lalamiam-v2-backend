package com.ctoutweb.lalamiam.infra.controller;

import com.ctoutweb.lalamiam.core.adapter.adminDisplayProfessionalDocument.IBoundariesAdapter;
import com.ctoutweb.lalamiam.core.useCase.impl.AdminDisplayProfessionalInscriptionDocumentUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

  private final AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase;

  public AdminController(AdminDisplayProfessionalInscriptionDocumentUseCase adminDisplayProfessionalInscriptionDocumentUseCase) {
    this.adminDisplayProfessionalInscriptionDocumentUseCase = adminDisplayProfessionalInscriptionDocumentUseCase;
  }


  @GetMapping("/get-professional-documents")
  public ResponseEntity<String> getProfessionalDocuement() {


    AdminDisplayProfessionalInscriptionDocumentUseCase.Input input = AdminDisplayProfessionalInscriptionDocumentUseCase
            .Input
            .getUseCaseInput(new IBoundariesAdapter.IBoundaryInputAdapter() {
      @Override
      public Long getProfessionalId() {
        return 1L;
      }
    });

    adminDisplayProfessionalInscriptionDocumentUseCase.execute(input);

    return new ResponseEntity<>("accés aux docuement", HttpStatus.OK);
  }
}
