package com.ctoutweb.lalamiam.infra.utility;

public class TextUtility {

  private TextUtility() {
    throw new IllegalStateException("Class utilitaire");
  }

  /**
   * Remplace un mot dans un text
   * @param text String - Text avec mot a remplacer
   * @param wordToReplace - mot à remplacer
   * @param word - mot de rempalcement
   * @return String - Text mis à jour
   */
  public static String replaceWordInText(String text, String wordToReplace, String word) {
    return text.replace(wordToReplace, word);
  }
}
