package com.ctoutweb.lalamiam.infra.service.impl;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageService {
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
