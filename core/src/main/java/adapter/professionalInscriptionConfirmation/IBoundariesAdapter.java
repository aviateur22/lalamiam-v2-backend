package adapter.professionalInscriptionConfirmation;

import adapter.IResponse;

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
