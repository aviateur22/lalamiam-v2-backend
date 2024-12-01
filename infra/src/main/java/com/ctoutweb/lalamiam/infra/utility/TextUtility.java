package com.ctoutweb.lalamiam.infra.utility;

import java.util.Random;

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

  /**
   * RandomText
   * @param randomWordLength int - Longueur du text
   * @return String
   */
  public static String generateRandomText(int randomWordLength) {
    Random rand = new Random();
    return rand.ints(48, 123)
            .filter(num->(num < 58 || num > 64) && (num < 91 || num > 96))
            .limit(randomWordLength)
            .mapToObj(c->(char) c)
            .collect(StringBuffer::new , StringBuffer::append, StringBuffer::append)
            .toString();
  }
}
