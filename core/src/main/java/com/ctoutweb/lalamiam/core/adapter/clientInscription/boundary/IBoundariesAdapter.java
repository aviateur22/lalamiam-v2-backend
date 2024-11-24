package com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary;

import com.ctoutweb.lalamiam.core.adapter.IResponse;

public interface IBoundariesAdapter {
  /**
   * Entée du useCase
   */
  public interface IBoundaryInputAdapter extends com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryInputAdapter {
    String getHashPassword();
    String getEmail();
    String getUserName();
  }

  /**
   * Sortie du useCase
   */
  public interface IBoundaryOutputAdapter extends IResponse {
    Long getUserId();

  }
}
