package com.ctoutweb.lalamiam.infra.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

  /**
   * Upload un fichier
   * @param file MultipartFile
   * @return String path d'acces du fichier
   */
  String uploadFile(MultipartFile file) throws IOException;

  /**
   * Récupération d'un fichier
   * @param path String
   * @return MultipartFile
   */
  MultipartFile getFile(String path);
}
