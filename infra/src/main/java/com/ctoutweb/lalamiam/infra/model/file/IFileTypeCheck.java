package com.ctoutweb.lalamiam.infra.model.file;

/**
 * Validation du fichier
 */
public interface IFileTypeCheck {

  /**
   * Bytes contenant le format du fichier
   * @param magicBytes byte[]
   * @return boolean
   */
  public boolean isFileExtensionValid(byte[] magicBytes) ;
}
