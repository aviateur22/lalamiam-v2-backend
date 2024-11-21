package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.infra.model.IApiLanguage;

/**
 * Language validé pour API
 */
public class ApiLanguageImpl implements IApiLanguage {
  String validatedLanguage;

  public ApiLanguageImpl(String validatedLanguage) {
    this.validatedLanguage = validatedLanguage;
  }

  @Override
  public String getValidatedLanguage() {
    return validatedLanguage;
  }

  public void setValidatedLanguage(String validatedLanguage) {
    this.validatedLanguage = validatedLanguage;
  }
}
