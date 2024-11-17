package com.ctoutweb.lalamiam.core.entity.image;

public enum ImageType {

  PNG,
  JPEG;

  /**
   * Renvoie le mimeType en fonction de l'image
   * @return String
   */
  public String getMimeType() {
    return switch (this) {
      case PNG -> "image/png";
      case JPEG -> "image/jpeg";
    };
  }
}
