package com.ctoutweb.lalamiam.infra.helper;

import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.service.helper.IProfessionalRegisterConfirmationHelper;
import com.ctoutweb.lalamiam.infra.service.helper.IProfessionalRegisterHelper;
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

  private final IProfessionalRegisterConfirmationHelper professionalRegisterConfirmationHelper;
  private final IProfessionalRegisterHelper professionalRegisterHelper;

  public AuthServiceHelper(
          IProfessionalRegisterConfirmationHelper professionalRegisterConfirmationHelper,
          IProfessionalRegisterHelper professionalRegisterHelper) {
    this.professionalRegisterConfirmationHelper = professionalRegisterConfirmationHelper;
    this.professionalRegisterHelper = professionalRegisterHelper;
  }

  /**
   * Validation des tokens envoyés pour validation de l'inscription d'un professionel
   * @param professionalEmail String - email professionel
   * @param emailToken String - token contenu dans l'email
   * @param urlToken String- token de url
   * @return boolean
   */
  public Boolean areProfessionalRegisterTokensValid(String professionalEmail, String emailToken, String urlToken) {
    // Verification des tokens
    boolean isEmailTokenValid = professionalRegisterConfirmationHelper
            .findUserRegisterToken(professionalEmail, CryptographyType.HASH)
            .isFrontEndTokenValid(emailToken);

    boolean isUrlTokenValid = professionalRegisterConfirmationHelper
            .findUserRegisterToken(professionalEmail, CryptographyType.ENCRYPT)
            .isFrontEndTokenValid(urlToken);

    return isEmailTokenValid && isUrlTokenValid;
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

}
