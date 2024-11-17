package entity.professionalInscriptionConfirmation.impl.boundary;

import adapter.professionalInscriptionConfirmation.IBoundariesAdapter.IBoundaryOutputAdapter;

public record BoundaryOutputImpl(boolean isConfirmationValid, String message) implements IBoundaryOutputAdapter {
  public static BoundaryOutputImpl getBoundaryInputImpl(boolean isInscriptionConfirm, String message) {
    return new BoundaryOutputImpl(isInscriptionConfirm, message);
  }

  @Override
  public boolean isConfirmationValid() {
    return isConfirmationValid;
  }

  @Override
  public String getResponseMessage() {
    return message;
  }
}
