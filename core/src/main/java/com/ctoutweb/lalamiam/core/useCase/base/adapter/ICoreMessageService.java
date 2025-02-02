package com.ctoutweb.lalamiam.core.useCase.base.adapter;

public interface ICoreMessageService {
  /**
   * Récupération d'un message
   * @param messageKey String - Clé du message a récupérer
   * @return String
   */
  String getMessage(String messageKey);
}
