package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.config.Language;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:application.properties"})
public class ApiLanguageConfig {
  private static final Logger LOGGER = LogManager.getLogger();

  private final Factory factory;
  @Value("${api.language}")
  private String apiLanguage;

  private String defaultApiLanguage = Language.FR.getLanguageCode();

  public ApiLanguageConfig(Factory factory) {
    this.factory = factory;
  }

  @Bean("apiLanguage")
  public IApiLanguage getApiLanguage() {
    String selectedLanguage = apiLanguage.toLowerCase();

    if(isApiLanguageValid(selectedLanguage)) {
      return factory.getImpl(selectedLanguage);
    }

    LOGGER.error(()->String.format("Le language %s n'est pas valide", selectedLanguage));
    return factory.getImpl(defaultApiLanguage);
  }

  /**
   * Vérification de la validité du language
   * @param languageToCheck String
   * @return Booelan
   */
  public boolean isApiLanguageValid(String languageToCheck) {
    for(Language language: Language.values()) {
      if(language.getLanguageCode().equals(languageToCheck)) {
        return true;
      }
    }
   return false;
  }

}
