package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.config.FileMessage;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.IApiMessage;
import com.ctoutweb.lalamiam.infra.utility.PropertiesUtility;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.WORD_TO_REPLACE;
import static com.ctoutweb.lalamiam.infra.utility.TextUtility.replaceWordInText;

@Configuration
public class MessageConfig {

  private final IApiLanguage apiLanguage;
  private final Factory factory;
  public MessageConfig(@Qualifier("apiLanguage")IApiLanguage apiLanguage, Factory factory) {
    this.apiLanguage = apiLanguage;
    this.factory = factory;
  }

  /**
   * Renvoie les messages disponible pour l'API
   * @return IAPiMessage
   */
  @Bean(name = "messages")
  public IApiMessage getMessages() {
    return factory.getImpl(loadMessages(this.apiLanguage));
  }

  /**
   * Récupération des messages
   * @param apiLanguage String - Langue des messages Api a charger
   * @return Properties
   */
  public Properties loadMessages(IApiLanguage apiLanguage) {
    Properties messages = new Properties();

    for(FileMessage fileMessage :FileMessage.values()) {
      String filePath = replaceWordInText(fileMessage.getMessageFilePath(), WORD_TO_REPLACE, apiLanguage.getValidatedLanguage());
      Properties properties = PropertiesUtility.getProperties(filePath);
      properties.entrySet().stream().forEach(entry->{
        messages.put(entry.getKey(), entry.getValue());
      });
    }

    return messages;
  }
}
