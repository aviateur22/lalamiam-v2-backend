package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApiLanguageConfigTest {

  private ApiLanguageConfig apiLanguageConfig = new ApiLanguageConfig(new Factory());
  @Test
  public void isApiLanguageValid_should_return_true() throws IOException {

    /**
     * given
     */
    String selectLanguage = "fr";

    /**
     * then
     */
    boolean isLanguageValid = apiLanguageConfig.isApiLanguageValid(selectLanguage);

    /**
     * then
     */
    Assertions.assertEquals(true, isLanguageValid);

  }
  @Test
  public void isApiLanguageValid_should_return_false() {
    /**
     * given
     */
    String selectLanguage = "be";

    /**
     * then
     */
    boolean isLanguageValid = apiLanguageConfig.isApiLanguageValid(selectLanguage);

    /**
     * then
     */
    Assertions.assertEquals(false, isLanguageValid);

  }

}
