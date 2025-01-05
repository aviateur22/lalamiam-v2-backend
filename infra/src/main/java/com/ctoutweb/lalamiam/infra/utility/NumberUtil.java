package com.ctoutweb.lalamiam.infra.utility;

import java.util.Random;

public class NumberUtil {
  /**
   * Renvoie un entier entre 2 valeurs
   * @param min int
   * @param max int
   * @return int
   */
  public static int generateNumberBetween(int min, int max) {
    Random random = new Random();
    return random.nextInt((max-min) + 1) + min;
  }

  public static boolean isStringValidLong(String numberStringFormat) {
    try {
      Long.parseLong(numberStringFormat);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
