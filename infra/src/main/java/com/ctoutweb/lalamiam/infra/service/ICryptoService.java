package com.ctoutweb.lalamiam.infra.service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

  byte[] generateRandomByte();
}
