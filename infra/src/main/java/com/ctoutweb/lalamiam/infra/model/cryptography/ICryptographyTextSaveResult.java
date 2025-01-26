package com.ctoutweb.lalamiam.infra.model.cryptography;

import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;

public interface ICryptographyTextSaveResult {
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
