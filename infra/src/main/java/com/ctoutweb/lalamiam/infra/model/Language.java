package com.ctoutweb.lalamiam.infra.model;

/**
 * Language disponible
 */
public enum Language {
  FR("fr"),
  EN("en");

  /**
   * Code Language
   */
  private String codeLanguage;
  private Language(String codeLanguage) {
    this.codeLanguage = codeLanguage;
  }

  /**
   * Renvoie le code language
   * @return String
   */
  public String getLanguageCode() {
    return  this.codeLanguage;
  }
}
