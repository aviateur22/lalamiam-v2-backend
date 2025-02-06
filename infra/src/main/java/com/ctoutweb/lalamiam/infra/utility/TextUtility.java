package com.ctoutweb.lalamiam.infra.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class TextUtility {
  private static final Logger LOGGER = LogManager.getLogger();
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

  /**
   * RandomText de longueyr variable
   * @param randomWordLengthMin int - Longueur min du text
   * @param randomWordLengthMin int - Longueur max du text
   * @return String
   */
  public static String generateRandomTextBetween(int randomWordLengthMin, int randomWordLengthMax) {
    Random rand = new Random();
    int randomWordLength = NumberUtil.generateNumberBetween(randomWordLengthMin, randomWordLengthMax);
    return rand.ints(48, 123)
            .filter(num->(num < 58 || num > 64) && (num < 91 || num > 96))
            .limit(randomWordLength)
            .mapToObj(c->(char) c)
            .collect(StringBuffer::new , StringBuffer::append, StringBuffer::append)
            .toString();
  }

  /**
   * Remplace une liste de mot dans 1 text
   * @param initialText String - Text devant être mise à jour
   * @param mapOfWordToReplace Map<String, String> - Mot devant être mis dans le text
   * @return String - Text mise à jour
   */
  public static String replaceWordInText(String initialText, Map<String, String> mapOfWordToReplace) {
    for(Map.Entry<String, String> k: mapOfWordToReplace.entrySet()) {
      LOGGER.debug(k.getKey());
      initialText =   initialText.replace(k.getKey(), k.getValue());
    }
    return initialText;
  }


  /**
   * Génération d'un String Aléatoire de type UUID
   * @return String
   */
  public static String getRandomNameUUID() {
    byte[] timeNow = ("time now" +" " + System.currentTimeMillis()).getBytes();
    return UUID.nameUUIDFromBytes(timeNow).toString();
  }
}
