package entity.validateUserCaptchaResponse.impl;

import adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryOutputAdapter;

public record BoundaryOutputImpl(boolean isClientResponseValid) implements IBoundaryOutputAdapter {
  public static BoundaryOutputImpl getBoundaryOutputImpl(boolean isClientResponseValid) {
    return new BoundaryOutputImpl(isClientResponseValid);
  }

  @Override
  public boolean getIsClientResponseValid() {
    return isClientResponseValid;
  }
}
