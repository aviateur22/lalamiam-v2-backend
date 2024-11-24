package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.impl.ApiLanguageImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class MessageConfigTest {

  @Test
  public void loadMessages() {
    /**
     * given
     */
    IApiLanguage apiLanguage = new ApiLanguageImpl("fr");

    /**
     * when
     */
    MessageConfig messageConfig = new MessageConfig(apiLanguage, new Factory());
    Properties messages = messageConfig.loadMessages(apiLanguage);

    /**
     * then
     */
    Assertions.assertNotNull(messages);
    Assertions.assertTrue(messages.size() > 0);
  }
}
