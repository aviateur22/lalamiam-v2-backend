package adapter.professionalInscription;

import adapter.IResponse;

import java.util.List;

public interface IBoundariesAdapter {

  /**
   * Entée du useCase
   */
  public interface IBoundaryInputAdapter extends adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter {
    String getName();
    String getFirstName();
    String getPhone();
    List<String> getDocuments();
  }

  /**
   * Sortie du useCase
   */
  public interface IBoundaryOutputAdapter extends IResponse {
    Long getProfessionalId();
  }
}
