package com.ctoutweb.lalamiam.core.useCase.base.gateway;

public interface ICoreMessageService {
  /**
   * Récupération d'un message
   * @param messageKey String - Clé du message a récupérer
   * @return String
   */
  String getMessage(String messageKey);
}
