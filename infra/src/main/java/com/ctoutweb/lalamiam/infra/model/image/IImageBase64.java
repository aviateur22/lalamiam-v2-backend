package com.ctoutweb.lalamiam.infra.model.image;

/**
 * Contenue image base64
 */
public interface IImageBase64 {

  /**
   * MimeType de l'image
   * ex: image/png
   * @return String
   */
  String getMimeType();

  /**
   * Image au format base64
   * @return String
   */
  String getBase64Format();
}
