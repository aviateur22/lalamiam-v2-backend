package com.ctoutweb.lalamiam.infra.model.impl;

import com.ctoutweb.lalamiam.infra.model.IMessageResponse;

public record MessageResponseImpl(String responseMessage) implements IMessageResponse {
  @Override
  public String getResponseMessage() {
    return responseMessage;
  }
}
