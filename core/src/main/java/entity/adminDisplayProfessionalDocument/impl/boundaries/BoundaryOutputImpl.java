package entity.adminDisplayProfessionalDocument.impl.boundaries;

import adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryOutputAdapter;
import entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument.IProfessionalDocument;

import java.io.File;

public record BoundaryOutputImpl(long professionalId, String email, String phone, File fileStatus) implements IBoundaryOutputAdapter {
  public static BoundaryOutputImpl getBoundaryOutput(IProfessionalDocument professionalInscriptionDocument) {
    return new BoundaryOutputImpl(
            professionalInscriptionDocument.getProfessionalId(),
            professionalInscriptionDocument.getProfessionalEmail(),
            professionalInscriptionDocument.getProfessionalPhone(),
            professionalInscriptionDocument.getProfessionalFileStatus()
    );
  }

  @Override
  public Long getProfessionalId() {
    return professionalId();
  }

  @Override
  public String getProfessionalEmail() {
    return email();
  }

  @Override
  public String getProfessionalPhone() {
    return phone();
  }
  @Override
  public File getProfessionalFileStatus() {
    return fileStatus();
  }
}
