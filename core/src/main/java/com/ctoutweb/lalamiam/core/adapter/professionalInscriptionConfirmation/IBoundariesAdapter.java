package com.ctoutweb.lalamiam.core.adapter.professionalInscriptionConfirmation;

import com.ctoutweb.lalamiam.core.adapter.IResponse;

public interface IBoundariesAdapter {
  public interface IBoundaryInputAdapter {
    public String getProfessionalEmailToken();
    public String getProfessionalUrlToken();
    public String getProfessionalEmail();
  }

  public interface IBoundaryOutputAdapter extends IResponse {
    public boolean isConfirmationValid();
  }

}
