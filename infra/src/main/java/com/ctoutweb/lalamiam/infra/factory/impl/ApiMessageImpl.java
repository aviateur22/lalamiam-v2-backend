package com.ctoutweb.lalamiam.infra.factory.impl;

import com.ctoutweb.lalamiam.infra.factory.IFactory;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.impl.ApiLanguageImpl;

public record ApiMessageImpl() implements IFactory<String, IApiLanguage> {
  @Override
  public IApiLanguage getImpl(String language) {
    return new ApiLanguageImpl(language);
  }
}
