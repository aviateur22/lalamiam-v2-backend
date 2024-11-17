package entity.createCaptchaImage;

import adapter.createImage.boundary.InputBoundaryAdapter;

public record InputBoundary(InputBoundaryAdapter inputBoundaryAdapter) implements InputBoundaryAdapter {
  @Override
  public String getTextToDraw() {
    return inputBoundaryAdapter.getTextToDraw();
  }
}
