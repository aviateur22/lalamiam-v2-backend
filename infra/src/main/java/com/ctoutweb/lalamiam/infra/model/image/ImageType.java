package com.ctoutweb.lalamiam.infra.model.image;

import com.ctoutweb.lalamiam.infra.model.image.impl.ImagePropertiesImpl;

/**
 * Image Type
 * PNG
 * JPEG
 * ....
 */
public enum ImageType {
  PNG(new ImagePropertiesImpl("png", MimeType.PNG)),
  JPEG(new ImagePropertiesImpl("jpeg", MimeType.JPEG));

  private IPropertiesImage imageProperties;
  private ImageType(IPropertiesImage imageProperties) {
    this.imageProperties = imageProperties;
  }

  public IPropertiesImage getImageProperties() {
    return this.imageProperties;
  }
}
