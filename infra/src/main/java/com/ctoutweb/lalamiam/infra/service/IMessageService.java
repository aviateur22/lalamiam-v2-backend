package com.ctoutweb.lalamiam.infra.service;

/**
 * Message disponible pour l'API
 */
public interface IMessageService {

  /**
   * Renvoie la valeur du message à partir de sa clé
   * @param messageKey String - Clé du message
   * @return String - Message
   */
  public String getMessage(String messageKey);
}
