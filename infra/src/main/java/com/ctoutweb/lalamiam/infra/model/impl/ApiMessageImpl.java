package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.infra.model.IApiMessage;

import java.util.Properties;

public class ApiMessageImpl implements IApiMessage {

  private Properties properties;
  public ApiMessageImpl(Properties properties) {
    this.properties =properties;
  }
  @Override
  public Properties getApiMessages() {
    return properties;
  }
}
