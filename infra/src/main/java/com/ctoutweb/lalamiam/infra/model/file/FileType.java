package com.ctoutweb.lalamiam.infra.model.file;

import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.*;

public enum FileType implements IFileTypeCheck {
  PDF(PDF_MAGIC_NUMBER, "pdf"),
  PNG(PNG_MAGIC_NUMBER, "png"),
  JPEG(JPEG_MAGIC_NUMBER,"jpeg");

  private byte[] magicBytes;

  private String fileExtension;

  FileType(byte[] magicBytes, String fileExtension) {
    this.magicBytes = magicBytes;
    this.fileExtension = fileExtension;
  }

  /**
   * Renvoie l'extension
   * @return String
   */
  public String getFileExtension() {
    return this.fileExtension;
  }

  /**
   * Vérification extension d'un fichier
   * @param fileMagicBytes byte[]
   * @return boolean
   */
  @Override
  public boolean isFileExtensionValid(byte[] fileMagicBytes) {
    if(fileMagicBytes.length < magicBytes.length)
      return  false;

    for (int i=0; i < fileMagicBytes.length; i++) {
      if (Byte.toUnsignedInt(fileMagicBytes[i]) != magicBytes[i])
        return false;
    }
    return true;
  }
}
