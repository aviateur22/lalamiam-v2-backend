package entity.adminDisplayProfessionalDocument.impl.entity;

import entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument.IProfessionalDocument;

import java.io.File;

public record ProfessionalInscriptionDocumentImpl(IProfessionalDocument professionalDocument) implements IProfessionalDocument {

  public static ProfessionalInscriptionDocumentImpl getProfessionalInscriptionDocumentImpl(IProfessionalDocument professionalDocument) {
    return new ProfessionalInscriptionDocumentImpl(professionalDocument);
  }
  ////////////////////////////////////////////

  @Override
  public Long getProfessionalId() {
    return professionalDocument.getProfessionalId();
  }

  @Override
  public String getProfessionalEmail() {
    return professionalDocument.getProfessionalEmail();
  }
  @Override
  public String getProfessionalPhone() {
    return professionalDocument.getProfessionalPhone();
  }

  @Override
  public File getProfessionalFileStatus() {
    return professionalDocument.getProfessionalFileStatus();
  }
}
