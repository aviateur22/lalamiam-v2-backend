package entity.createCaptchaImage;

import adapter.createImage.boundary.OutputBoundaryAdapter;

import java.awt.image.BufferedImage;

public record OutputBoundary(BufferedImage createdImage) implements OutputBoundaryAdapter {
  @Override
  public BufferedImage getGeneratedImage() {
    return createdImage;
  }
}
