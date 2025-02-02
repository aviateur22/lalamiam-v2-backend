package com.ctoutweb.lalamiam.infra.core.service;

import com.ctoutweb.lalamiam.core.useCase.base.adapter.ICoreEmailService;
import com.ctoutweb.lalamiam.infra.service.IEmailService;
import org.springframework.stereotype.Component;

@Component
public class CoreEmailServiceImpl implements ICoreEmailService {
  private final IEmailService emailService;

  public CoreEmailServiceImpl(IEmailService emailService) {
    this.emailService = emailService;
  }

  @Override
  public void sendActivateProfessionalAccountEmail(String professionalEmail) {
    emailService.sendEmail();
  }
}
