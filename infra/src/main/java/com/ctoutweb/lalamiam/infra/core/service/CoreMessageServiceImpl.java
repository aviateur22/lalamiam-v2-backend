package com.ctoutweb.lalamiam.infra.core.service;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreMessageService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.springframework.stereotype.Component;

@Component
public class CoreMessageServiceImpl implements ICoreMessageService {
 private final IMessageService messageService;

  public CoreMessageServiceImpl(IMessageService messageService) {
    this.messageService = messageService;
  }

  @Override
  public String getMessage(String messageKey) {
    return messageService.getMessage(messageKey);
  }
}
