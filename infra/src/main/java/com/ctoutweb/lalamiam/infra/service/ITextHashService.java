package com.ctoutweb.lalamiam.infra.service;

public interface ITextHashService {
  /**
   * Hash une chaine
   * @param textToHash String - Text a hasher
   * @return Strong - Text hashé
   */
  public String hashText(String textToHash);

  /**
   * Vérification d'un hash et text
   * @param hash String
   * @param plainText String
   * @return Booelean
   */
  public boolean isHashValid(String plainText, String hash);
}
