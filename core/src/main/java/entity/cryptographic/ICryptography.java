package entity.cryptographic;


public interface ICryptography {

  /**
   * Resultat de la cryptographie et sauvegarde
   */
  public interface ICryptographySaveResult {

    /**
     * Text apres cryptographie
     * @return String
     */
    String getCryptographicText();

    /**
     * Type de cryptographie utilisé
     * @return
     */
    CryptographicType getCryptographicType();

    /**
     * Id de sauvegarde
     * @return Long
     */
    Long getResponseId();
  }
}
