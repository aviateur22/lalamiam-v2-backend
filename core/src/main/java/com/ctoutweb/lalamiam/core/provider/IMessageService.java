package com.ctoutweb.lalamiam.core.provider;

public interface IMessageService {
  /**
   * Récupération d'un message
   * @param messageKey String - Clé du message a récupérer
   * @return String
   */
  String getMessage(String messageKey);

  /**
   * Log un message - Level error
   * @param message message a logger
   */
  void logError(String message);

  /**
   * Log un message - Level Info
   * @param message message a logger
   */
  void logInfo(String message);

  /**
   * Log un message - Level debug
   * @param message message a logger
   */
  void logDebug(String message);
}
