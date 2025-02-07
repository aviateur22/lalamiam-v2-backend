package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.auth.IRegisterFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface IFileService {

  /**
   * Upload un fichier
   * @param documentFile InputStream
   * @param fileSize Long
   * @return String path d'acces du fichier
   */
  String uploadFile(InputStream documentFile, Long fileSize);

  /**
   * Récupération d'un fichier
   * @param path String
   * @return MultipartFile
   */
  MultipartFile getFile(String path);
}
