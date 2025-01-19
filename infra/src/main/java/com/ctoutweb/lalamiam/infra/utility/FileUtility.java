package com.ctoutweb.lalamiam.infra.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

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
}
