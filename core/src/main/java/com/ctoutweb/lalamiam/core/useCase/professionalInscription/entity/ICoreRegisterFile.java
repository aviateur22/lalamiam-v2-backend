package com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity;

import java.io.InputStream;

/**
 * Fichier pour enrgistrement professionnel
 */
public interface ICoreRegisterFile {
  InputStream getRegisterFile();
  Long getFileSize();
  String getFileExtension();
}
