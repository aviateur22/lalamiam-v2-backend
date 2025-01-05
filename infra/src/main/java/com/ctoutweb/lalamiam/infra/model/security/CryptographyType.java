package com.ctoutweb.lalamiam.infra.model.security;

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
}
