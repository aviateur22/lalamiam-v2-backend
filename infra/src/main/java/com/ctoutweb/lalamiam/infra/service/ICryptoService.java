package com.ctoutweb.lalamiam.infra.service;

/**
 * Service permattant de gerer la cryptographie des données
 */
public interface ICryptoService {
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

  /**
   * Chiffrement d'un text
   * @param text String - Text à chiffrer
   * @return String
   */
  public String encrypt(String text, byte[] iv);

  /**
   * Dechiffrement d'un text
   * @param cipherText String - Text chiffré à déchiffrer
   * @param iv byte[] - Clé IV
   * @return  String
   */
  public String decrypt(String cipherText, byte[] iv);

  /**
   * Génration d'un tableau de random byte
   * @return byte[]
   */
  byte[] generateRandomByte();

  /**
   * Récupération dun tableau de byte à prtir d'un text encodé en base64
   * @param base64Text String - Text encodé en base 64
   * @return byte[]
   */
  byte[] getByteArrayFromBase64(String base64Text);
}
