package com.ctoutweb.lalamiam.infra.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FrontEndLinkHelper {

  @Value("${activate.user.account.link}")
  private String activateUserAccountLink;

  @Value("${domain.front}")
  private String domainFront;
  @Value("${professional.confirm.register.link}")
  private String professionalConfirmationRegisterLink;
  @Value("${change.account.password.link}")
  private String changeAccountPasswordLink;

  /**
   * Renvoie le front URL pour confirmer la création d'un compte professionel
   * @param email String
   * @param urlToken String
   * @return String
   */
  public String getProfessionalConfirmationRegisterLink(String email, String urlToken) {
    return String.format(professionalConfirmationRegisterLink , domainFront, email, urlToken);
  }
}
