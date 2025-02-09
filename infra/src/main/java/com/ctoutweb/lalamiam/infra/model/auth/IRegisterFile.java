package com.ctoutweb.lalamiam.infra.model.auth;

import java.io.InputStream;

/**
 * Fichier pour enrgistrement professionnel
 */
public interface IRegisterFile {
  InputStream getRegisterFile();
  Long getFileSize();
  String getFileExtension();
}
