package com.ctoutweb.lalamiam.core.entity.createCaptchaImage;

import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.InputBoundaryAdapter;

public record InputBoundary(InputBoundaryAdapter inputBoundaryAdapter) implements InputBoundaryAdapter {
  @Override
  public String getTextToDraw() {
    return inputBoundaryAdapter.getTextToDraw();
  }
}
