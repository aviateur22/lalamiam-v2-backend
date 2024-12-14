package com.ctoutweb.lalamiam.infra.model.image.impl;

import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;

public record ImageBase64Impl(String mimeType, String base64Format) implements IImageBase64 {
  @Override
  public String getMimeType() {
    return mimeType;
  }

  @Override
  public String getBase64Format() {
    return base64Format;
  }
}
