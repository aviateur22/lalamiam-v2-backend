package com.ctoutweb.lalamiam.infra.service.impl;
import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.infra.model.IApiMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements IMessageService, com.ctoutweb.lalamiam.infra.service.IMessageService {

  private final IApiMessage apiMessage;

  public MessageServiceImpl(IApiMessage apiMessage) {
    this.apiMessage = apiMessage;
  }

  @Override
  public String getMessage(String messageKey) {
    return apiMessage.getApiMessages().getProperty(messageKey);
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
