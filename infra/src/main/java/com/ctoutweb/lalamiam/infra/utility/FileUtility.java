package com.ctoutweb.lalamiam.infra.utility;

import com.ctoutweb.lalamiam.infra.model.file.FileType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtility {
  private static final Logger LOGGER = LogManager.getLogger();
  /**
   * Renvoie le contenu d'un fichier en String
   * @param filePath - String Path du fichier qui est à lire
   * @return  String
   */
  public static String readFile(String filePath) {
    try{
      if(filePath == ""){
        LOGGER.error(()->String.format("Impossible de renvoyer un String avec le fichier: %s " , filePath));
        return "";
      }
      InputStream fileStream = new ClassPathResource(filePath).getInputStream();
      StringBuilder resultStringBuilder = new StringBuilder();
      try (BufferedReader br = new BufferedReader(new InputStreamReader(fileStream))) {
        String line;
        while ((line = br.readLine()) != null) {
          resultStringBuilder.append(line).append("\n");
        }
      }
      return resultStringBuilder.toString();
    } catch (IOException exception) {
      LOGGER.error(()->String.format("Impossible de renvoyer un String avec le fichier: %s " , filePath));
      return "";
    }
  }

  /**
   * Valide si un fichier est bien de type PDF
   * @param fileStream InputStream - Fichier a vérifier
   * @return Boolean
   * @throws IOException
   */
  public static boolean isFilePdf(InputStream fileStream) throws IOException {
      byte[] magicBytes = new byte[4];
      fileStream.read(magicBytes);
      fileStream.close();

      return FileType.PDF.isFileExtensionValid(magicBytes);
  }

  /**
   * Récupération extension fichier a partir d'un Multipart
   * @param uploadFile MultipartFile
   * @return String - Extension du fichier
   */
  public static String getFileExtension(MultipartFile uploadFile) {
    String originalFilename = uploadFile.getOriginalFilename();
    if (originalFilename != null && originalFilename.contains(".")) {
      return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
    return "";
  }
}
