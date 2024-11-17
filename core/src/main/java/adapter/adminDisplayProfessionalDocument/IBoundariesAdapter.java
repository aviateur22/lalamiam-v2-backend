package adapter.adminDisplayProfessionalDocument;

import java.io.File;

public interface IBoundariesAdapter {

  /**
   * Entée du useCase
   */
  public interface IBoundaryInputAdapter {
    public Long getProfessionalId();
  }

  /**
   * Sortie du useCase
   */
  public interface IBoundaryOutputAdapter extends IBoundariesAdapter.IBoundaryInputAdapter {
    public String getProfessionalEmail();
    public String getProfessionalPhone();
    public File getProfessionalFileStatus();

  }
}
