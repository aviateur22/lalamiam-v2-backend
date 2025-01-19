package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;

import java.util.Map;

public interface IEmailService {
  /**
   * Generation du corps de l'email à partir d'un template.
   * Le template aura des mise à jour de données
   * @param type HtmlTemplateType - Type de email a envoyer
   * @param wordsToReplaceInHtmlTemplate Map<String, String> - List
   * @return
   */
  IEmailService replaceWordInHtmlTemplate(HtmlTemplateType type, Map<String, String> wordsToReplaceInHtmlTemplate);

  /**
   * Mise à jour des données pour envoyer un email : sujet du mail et email destinataire,
   * @param to String - Destinataire du mail
   * @param type HtmlTemplateType - Type de email a envoyer (activation, ...)
   * @return IEmailFormater
   */
  IEmailService setEmailInformation(HtmlTemplateType type, String to);

  /**
   * Envoie d'un email
   */
  public void sendEmail();
}
