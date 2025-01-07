package com.ctoutweb.lalamiam.infra.model.email;

public enum HtmlTemplateType {
  ACCOUNT_ACTIVATION("activationAccount_!%!lang!%!.html"),
  LOGIN_CONNEXION_ALERT("badLoginAttempt_!%!lang!%!.html"),
  CHANGE_PASSWORD("lostPasswordMailing_!%!lang!%!.html");
  private String fileName;
  private HtmlTemplateType(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Etablie le nom du template html
   * @param validateLanguage String - Le language du template HTML
   * @return String
   */
  private String composeFileName(String validateLanguage) {
    return this.fileName.replace("!%!lang!%!", validateLanguage);
  }

  /**
   * Renvoie le nom du template html a charger
   * @param validateLanuage String - Le language du template HTML
   * @return String
   */
  public String getFileName(String validateLanuage) {
    return composeFileName(validateLanuage);
  }
}
