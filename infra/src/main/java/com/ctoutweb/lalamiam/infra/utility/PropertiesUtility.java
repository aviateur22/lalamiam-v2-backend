package com.ctoutweb.lalamiam.infra.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesUtility {

  private static final Logger LOGGER = LogManager.getLogger();
  private PropertiesUtility() {
    throw new IllegalStateException("Class utilitaire");
  }

  /**
   * Chargement des propriétés d'un ficher .properties
   * @param fileName String - Nom du fichier properties
   * @return Properties
   */
  public static Properties getProperties(String fileName) {

    try (InputStream input = PropertiesUtility.class.getClassLoader().getResourceAsStream(fileName)) {

      if (input == null) {
        LOGGER.info(String.format("Aucun fichier de propriété %s de disponible", fileName));
        return null;
      }

      // Chargement des données avec un encodage en UTF-8
      try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
        Properties properties = new Properties();
        properties.load(reader);

        return properties;
      }
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

}
