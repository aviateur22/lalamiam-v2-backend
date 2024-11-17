package com.ctoutweb.lalamiam.core.util;

import java.util.Random;

public class TextUtil {
  /**
   * Generation text aléatoire en miniscule
   * @param textLength INTEGER - longueur du text
   * @return String
   */
  public static String generateTextToLowerCase(int textLength) {
    Random rand = new Random();
    String str = rand.ints(48, 123)
            .filter(num->(num < 58 || num > 64) && (num < 91 || num > 96))
            .limit(textLength)
            .mapToObj(c->(char) c)
            .collect(StringBuffer::new , StringBuffer::append, StringBuffer::append)
            .toString();
    return str.toLowerCase();
  }

  /**
   * Generation text aléatoire en miniscule
   * @param textLength INTEGER - longueur du text
   * @return String
   */
  public static String generateText(int textLength) {
    Random rand = new Random();
    return rand.ints(48, 123)
            .filter(num->(num < 58 || num > 64) && (num < 91 || num > 96))
            .limit(textLength)
            .mapToObj(c->(char) c)
            .collect(StringBuffer::new , StringBuffer::append, StringBuffer::append)
            .toString();
  }
}
