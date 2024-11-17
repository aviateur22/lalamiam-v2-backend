package com.ctoutweb.lalamiam.core.entity.createCaptchaImage;

import com.ctoutweb.lalamiam.core.adapter.createImage.boundary.OutputBoundaryAdapter;

import java.awt.image.BufferedImage;

public record OutputBoundary(BufferedImage createdImage) implements OutputBoundaryAdapter {
  @Override
  public BufferedImage getGeneratedImage() {
    return createdImage;
  }
}
