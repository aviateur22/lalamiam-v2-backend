package com.ctoutweb.lalamiam.infra.core.service;

import com.ctoutweb.lalamiam.core.useCase.base.gateway.ICoreEmailService;
import com.ctoutweb.lalamiam.infra.service.IEmailService;
import com.ctoutweb.lalamiam.infra.service.helper.IProfessionalRegisterHelper;
import com.ctoutweb.lalamiam.infra.service.helper.IUserRegisterHelper;
import org.springframework.stereotype.Component;

@Component
public class CoreEmailServiceImpl implements ICoreEmailService {
  private final IEmailService emailService;
  private final IUserRegisterHelper userRegisterHelper;
  private final IProfessionalRegisterHelper professionalRegisterHelper;

  public CoreEmailServiceImpl(
          IEmailService emailService,
          IUserRegisterHelper userRegisterHelper,
          IProfessionalRegisterHelper professionalRegisterHelper) {
    this.emailService = emailService;
    this.userRegisterHelper = userRegisterHelper;
    this.professionalRegisterHelper = professionalRegisterHelper;
  }

  @Override
  public void sendClientRegisterActivationEmail(String clientEmail) {
    userRegisterHelper
            .findUserInformation(clientEmail)
            .generateTokenRegistration()
            .tokenCryptography()
            .saveGeneratedEncryptTokenWithUser()
            .sendRegisterEmail(clientEmail);
  }

  @Override
  public void sendProfessionalRegisterConfirmationEmail(String professionalEmail) {
    professionalRegisterHelper
            .findProfessionalInformation(professionalEmail)
            .generateTokenForProfessionalRegister()
            .tokenCryptography()
            .saveGeneratedHashTokenWithUser()
            .saveGeneratedEncryptTokenWithUser()
            .sendRegisterEmail(professionalEmail);
  }

  @Override
  public void sendActivateProfessionalAccountEmail(String professionalEmail) {
    emailService.sendEmail();
  }
}
