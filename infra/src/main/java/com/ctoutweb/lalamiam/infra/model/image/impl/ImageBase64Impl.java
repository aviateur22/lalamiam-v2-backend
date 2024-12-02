package com.ctoutweb.lalamiam.infra.model.image.impl;

import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;

public record ImageBase64Impl(String mimeType, String imageBAse64) implements IImageBase64 {
  @Override
  public String getMimeType() {
    return mimeType;
  }

  @Override
  public String getBase64Format() {
    return imageBAse64;
  }
}
