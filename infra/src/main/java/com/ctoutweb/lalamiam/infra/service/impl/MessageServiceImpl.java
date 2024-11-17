package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.service.IMessageServiceInfra;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageServiceInfra {
  @Override
  public String getMessage(String messageKey) {
    return null;
  }

  @Override
  public void logError(String message) {

  }

  @Override
  public void logInfo(String message) {

  }

  @Override
  public void logDebug(String message) {

  }
}
