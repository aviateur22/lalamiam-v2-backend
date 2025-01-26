package com.ctoutweb.lalamiam.infra.model.security;

import java.util.Arrays;

/**
 * Type de cryptographie disponible
 */
public enum CryptographyType {
  HASH("hash"),
  ENCRYPT("encrypt");

  private String name;

  private CryptographyType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public static CryptographyType findCryptotype(String cryptoName) {
    return Arrays.stream(CryptographyType.values())
            .filter(crypto->crypto.name.equalsIgnoreCase(cryptoName))
            .findFirst()
            .orElse(null);
  }
}
