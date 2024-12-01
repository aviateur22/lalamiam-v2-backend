package com.ctoutweb.lalamiam.infra.model.image;

/**
 * Configuration fichier image
 */
public interface IPropertiesImage {
  /**
   * Extension fichier image
   * ex: png, jpeg..
   * @return String
   */
  String getExtension();

  /**
   * Mimetype du fichier image
   * ex: image/png....
   * @return MimeType
   */
  MimeType getMimeType();

}
