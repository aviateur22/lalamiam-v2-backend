package com.ctoutweb.lalamiam.infra.model.image.impl;

import com.ctoutweb.lalamiam.infra.model.image.IPropertiesImage;
import com.ctoutweb.lalamiam.infra.model.image.MimeType;

public record ImagePropertiesImpl(String name, MimeType mimeType) implements IPropertiesImage {
  @Override
  public String getExtension() {
    return name;
  }

  @Override
  public MimeType getMimeType() {
    return mimeType;
  }
}
