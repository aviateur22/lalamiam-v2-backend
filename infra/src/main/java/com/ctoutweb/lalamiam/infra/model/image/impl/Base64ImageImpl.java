package com.ctoutweb.lalamiam.infra.model.image.impl;

import com.ctoutweb.lalamiam.infra.model.image.IBase64Image;

public record Base64ImageImpl() implements IBase64Image {
  @Override
  public String getMimeType() {
    return null;
  }

  @Override
  public String getBase64Format() {
    return null;
  }
}
