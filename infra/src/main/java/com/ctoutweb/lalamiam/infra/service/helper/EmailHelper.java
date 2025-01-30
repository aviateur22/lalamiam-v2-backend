package com.ctoutweb.lalamiam.infra.service.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class EmailHelper {
  @Value("${application.name}")
  private String applicationName;

  /**
   * Génération d'un liste de mot pour l'envoie d'un email de type confirmation d'inscription pour un professionel
   * @param email String - Email du profrssionel
   * @param confirmationToken String - Token de confirmation
   * @param link String - Lien URL pour confirmer la création du compte pro
   * @return Map<String, String>
   */
  public Map<String, String> listOfWordCaseRegisterConfirmation(String email, String confirmationToken, String link) {
    // Generation d'un email d'alerte
    Map<String, String> listWordsToReplaceInHtmlTemplate = new HashMap<>();

    listWordsToReplaceInHtmlTemplate.put("!%!email!%!", email);
    listWordsToReplaceInHtmlTemplate.put("!%!appName!%!", applicationName.toUpperCase());
    listWordsToReplaceInHtmlTemplate.put("!%!confirmationToken!%!", confirmationToken);
    listWordsToReplaceInHtmlTemplate.put("!%!link!%!", link);
    listWordsToReplaceInHtmlTemplate.put("!%!appName!%!", applicationName.toUpperCase());
    listWordsToReplaceInHtmlTemplate.put("!%!appName!%!", applicationName.toUpperCase());

    return listWordsToReplaceInHtmlTemplate;
  }

  /**
   * Génération d'un liste de mot pour l'envoie d'un email de type connexion failed
   * @param email String - Email de la personne ayant plusieurs connexion en echec
   * @return Map<String, String>
   */
  public Map<String, String> listOfWordToReplaceCaseLoginAttemptFailed(String email) {
    // Generation d'un email d'alerte
    Map<String, String> listWordsToReplaceInHtmlTemplate = new HashMap<>();

    listWordsToReplaceInHtmlTemplate.put("!%!year!%!", String.valueOf(LocalDateTime.now().getYear()));
    listWordsToReplaceInHtmlTemplate.put("!%!email!%!", email);
    listWordsToReplaceInHtmlTemplate.put("!%!email!%!", email);
    listWordsToReplaceInHtmlTemplate.put("!%!appName!%!", applicationName.toUpperCase());

    return listWordsToReplaceInHtmlTemplate;
  }

  /**
   * Génération d'un liste de mot pour l'envoie d'un email de type connexion failed
   * @param email String - Email de la personne ayant plusieurs connexion en echec
   * @return Map<String, String>
   */
  public Map<String, String> listOfWordToReplaceCaseLostPasswordEmail(String email, String link) {
    // Generation d'un email d'alerte
    Map<String, String> listWordsToReplaceInHtmlTemplate = new HashMap<>();

    listWordsToReplaceInHtmlTemplate.put("!%!year!%!", String.valueOf(LocalDateTime.now().getYear()));
    listWordsToReplaceInHtmlTemplate.put("!%!link!%!", link);
    listWordsToReplaceInHtmlTemplate.put("!%!email!%!", email);
    listWordsToReplaceInHtmlTemplate.put("!%!appName!%!", applicationName.toUpperCase());

    return listWordsToReplaceInHtmlTemplate;
  }
}
