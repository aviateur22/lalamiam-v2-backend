package com.ctoutweb.lalamiam.infra.model;

import com.ctoutweb.lalamiam.infra.model.impl.ApiLanguageImpl;
import com.ctoutweb.lalamiam.infra.model.impl.ApiMessageImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Factory {


  public IApiLanguage getImpl(String language) {
    return new ApiLanguageImpl(language);
  }
  public IApiMessage getImpl(Properties properties) {
    return new ApiMessageImpl(properties);
  }


}
