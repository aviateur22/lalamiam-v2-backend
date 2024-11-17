package common;

public class Function {

  /**
   * Vérification qu'un text ne possede pas de digit
   * @param text String
   * @return boolean
   */
  public static boolean hasNoDigits(String text) {
    return !text.matches(".*\\d.*");
  }
}
