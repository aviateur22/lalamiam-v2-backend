package entity.clientInscription.impl.boundaries;

import adapter.IResponse;
import adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryOutputAdapter;

public record BoundaryOutputImpl(String message, Long userId) implements IBoundaryOutputAdapter, IResponse {

  public static BoundaryOutputImpl getBoundaryOutputImpl(String message, Long userId) {
    return new BoundaryOutputImpl(message, userId);
  }
  @Override
  public String getResponseMessage() {
    return message;
  }

  @Override
  public Long getUserId() {
    return userId;
  }
}
