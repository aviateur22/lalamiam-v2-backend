package com.ctoutweb.lalamiam.infra.service.helper;

import com.ctoutweb.lalamiam.infra.dto.ReinitializeLostPasswordDto;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHelper {
  private static final Logger LOGGER = LogManager.getLogger();
  @Value("${captcha.iv.key}")
  String captchaIvKey;

  @Value("${zone.id}")
  String zoneId;

  private final IMessageService messageService;
  private final IProfessionalRegisterConfirmationHelper professionalRegisterConfirmationHelper;
  private final IProfessionalRegisterHelper professionalRegisterHelper;
  private final ILostPasswordHelper lostPasswordHelper;
  private final IReinitializeLostPassword reinitializeLostPassword;
  private final Factory factory;

  public AuthServiceHelper(
          IMessageService messageService,
          IProfessionalRegisterConfirmationHelper professionalRegisterConfirmationHelper,
          IProfessionalRegisterHelper professionalRegisterHelper,
          ILostPasswordHelper lostPasswordHelper,
          IReinitializeLostPassword reinitializeLostPassword, Factory factory) {
    this.messageService = messageService;
    this.professionalRegisterConfirmationHelper = professionalRegisterConfirmationHelper;
    this.professionalRegisterHelper = professionalRegisterHelper;
    this.lostPasswordHelper = lostPasswordHelper;
    this.reinitializeLostPassword = reinitializeLostPassword;
    this.factory = factory;
  }

  /**
   * Validation des tokens envoyés pour validation de l'inscription d'un professionel
   * @param professionalEmail String - email professionel
   * @param frontEndEmailToken String - token contenu dans l'email
   * @param frontEndUrlToken String- token de url
   * @return boolean
   */
  public Boolean areProfessionalRegisterTokensValid(String professionalEmail, String frontEndEmailToken, String frontEndUrlToken) {

    return professionalRegisterConfirmationHelper
            .findUserRegisterToken(professionalEmail)
            .isEmailTokenValid(frontEndEmailToken)
            .isUrlTokenValid(frontEndUrlToken)
            .deleteTokenOnSuccess()
            .areTokensValid();
  }

  /**
   * Generation des Token de l'email + URL
   * Envoie Email de confirmation d'inscription
   **/
  public void finalizeProfessionalRegister(String professionalEmail) {

    professionalRegisterHelper
            .findProfessionalInformation(professionalEmail)
            .generateTokenForProfessionalRegister()
            .tokenCryptography()
            .saveGeneratedHashTokenWithUser()
            .saveGeneratedEncryptTokenWithUser()
            .sendRegisterEmail(professionalEmail);
  }

  /**
   * Generation des Token de l'email + URL
   * Envoie Email pour regenerer son mot de passe
   **/
  public IMessageResponse lostPasswordMailing(String email) {

    lostPasswordHelper
            .findUserInformation(email)
            .generateRenewalPasswordToken()
            .encryptToken()
            .saveGeneratedEncryptTokenWithUser()
            .sendLostPasswordEmail(email);

    return factory.getMessageResponseImpl(messageService.getMessage("lost.password.mail.send"));

  }

  /**
   * Mise à jour du mot de passe
   * @param dto ReinitializeLostPasswordDto
   */
  public void reinitializeLostPassword(ReinitializeLostPasswordDto dto) {
    this.reinitializeLostPassword
            .findUserToken(dto.email())
            .isFrontEndTokenValid(dto.urlToken())
            .deleteUserToken()
            .updatePassword(dto.password());
  }

}
