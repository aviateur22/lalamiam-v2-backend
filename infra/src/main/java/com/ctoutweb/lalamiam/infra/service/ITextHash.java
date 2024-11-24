package com.ctoutweb.lalamiam.infra.service;

public interface ITextHash {
  /**
   * Hash une chaine
   * @param textToHash String - Text a hasher
   * @return Strong - Text hashé
   */
  public String hashText(String textToHash);
}
