package com.ctoutweb.lalamiam.infra.service.helper;

public interface IUpdatePassword {

  /**
   * Sauvegarde du nouveau mot de passe
   * @param password String - PlainText - Nouveau mot de passe
   */
  public void updatePassword(String password);
}
