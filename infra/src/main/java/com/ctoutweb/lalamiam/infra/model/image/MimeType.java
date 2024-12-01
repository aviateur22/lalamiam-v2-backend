package com.ctoutweb.lalamiam.infra.model.image;

/**
 * MimeType
 * ...
 */
public enum MimeType {
  PNG("image/png"),
  JPEG("image/jpeg");

  private String value;
  private MimeType(String value) {
    this.value = value;
  }

  public String getName() {
    return this.value;
  }
}
