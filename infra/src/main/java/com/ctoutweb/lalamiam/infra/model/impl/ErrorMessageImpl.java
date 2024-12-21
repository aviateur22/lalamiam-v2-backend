package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.infra.model.IErrorMessage;

public record ErrorMessageImpl(String error) implements IErrorMessage {
  @Override
  public String getError() {
    return error;
  }
}
