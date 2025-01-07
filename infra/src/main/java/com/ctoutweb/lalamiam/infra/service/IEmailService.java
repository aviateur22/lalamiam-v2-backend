package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;

import java.util.Map;

public interface IEmailService {
  /**
   * Envoie d'un email
   * @param mailSubject - String
   * @param to - destinataire
   * @param mailContent String
   * @param exceptionMessage - Message d'exception
   */
  public void sendEmail(
          String mailSubject,
          String to,
          String mailContent,
          String exceptionMessage
  );

  /**
   * Récupération d'un templateHtml et évaluation des variables à remplacer
   * @param type - HtmlTemplateType
   * @param wordsToReplaceInHtmlTemplate Map<String, String>
   * @return String - TemplateHtml mis a jour
   */
  public String generateHtml(
          HtmlTemplateType type,
          Map<String, String> wordsToReplaceInHtmlTemplate
  );
}
