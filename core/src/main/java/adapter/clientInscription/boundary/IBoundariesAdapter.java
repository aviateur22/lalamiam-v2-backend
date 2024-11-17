package adapter.clientInscription.boundary;

import adapter.IResponse;

public interface IBoundariesAdapter {
  /**
   * Entée du useCase
   */
  public interface IBoundaryInputAdapter extends adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryInputAdapter {
    String getHashPassword();
    String getEmail();
    String getName();
  }

  /**
   * Sortie du useCase
   */
  public interface IBoundaryOutputAdapter extends IResponse {
    Long getUserId();

  }
}
