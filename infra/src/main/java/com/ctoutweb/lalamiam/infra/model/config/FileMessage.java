package com.ctoutweb.lalamiam.infra.model.config;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.*;

/**
 * Fichiers message de disponible
 */
public enum FileMessage {
  EXCEPTION(EXCEPTION_MESSAGE_FILE),
  EMAIL(EMAIL_MESSAGE_FILE),
  SUCCESS(SUCCESS_MESSAGE_FILE),
  VALIDATOR(VALIDATOR_MESSAGE_FILE),
  CAPTCHA(CAPTCHA_MESSAGE_FILE);

  private String messageFilePath;
  private FileMessage(String messageFilePath) {
    this.messageFilePath = messageFilePath;
  }

  /**
   * Récupération du FilePath
   * @return String
   */
  public String getMessageFilePath() {
    return this.messageFilePath;
  }
}
