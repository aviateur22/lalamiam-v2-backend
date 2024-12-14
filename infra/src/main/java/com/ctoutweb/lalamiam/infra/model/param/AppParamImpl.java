package com.ctoutweb.lalamiam.infra.model.param;

public record AppParamImpl(String language) implements IAppParam {

  @Override
  public String getLanguage() {
    return language;
  }
}
