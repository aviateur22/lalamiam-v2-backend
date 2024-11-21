package com.ctoutweb.lalamiam.infra.service;

/**
 * Message disponible pour l'API
 */
public interface IMessageService {
  /**
   * Chargement de tous les messages pour la réponse client
   * @param apiLanguage String - Language validé dans API
   */
  public void loadApplicationMessages(String apiLanguage);

  /**
   * Renvoie la valeur du message à partir de sa clé
   * @param messageKey String - Clé du message
   * @return String - Message
   */
  public String getMessage(String messageKey);
}
