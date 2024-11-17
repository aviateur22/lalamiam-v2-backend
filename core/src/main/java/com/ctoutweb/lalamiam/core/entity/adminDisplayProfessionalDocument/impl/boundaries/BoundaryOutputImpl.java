package com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.impl.boundaries;

import com.ctoutweb.lalamiam.core.adapter.adminDisplayProfessionalDocument.IBoundariesAdapter.IBoundaryOutputAdapter;
import com.ctoutweb.lalamiam.core.entity.adminDisplayProfessionalDocument.IAdminDisplayProfessionalDocument.IProfessionalDocument;

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
