package com.ctoutweb.lalamiam.core.entity.validateUserCaptchaResponse.impl;

import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryInputAdapter;

public record BoundaryInputImpl(
        String clientResponse,
        String hashOrDecrypteCaptchaResponse,
        CryptographicType cryptographicType,
        String captchaToken,
        String captchaTokenSeparator
) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(
          String captchaResponseByUser,
          String hashOrDecrypteCaptchaResponse,
          CryptographicType cryptographicType,
          String captchaToken,
          String captchaTokenSeparator
  ) {
    return new BoundaryInputImpl(captchaResponseByUser, hashOrDecrypteCaptchaResponse, cryptographicType, captchaToken, captchaTokenSeparator);
  }

  @Override
  public String getCaptchaResponseByUser() {
    return clientResponse;
  }

  @Override
  public String getHashOrDecrypteCaptchaResponse() {
    return hashOrDecrypteCaptchaResponse;
  }

  @Override
  public CryptographicType getCryptographicType() {
    return cryptographicType;
  }

  @Override
  public String getCaptchaToken() {
    return captchaToken;
  }

  @Override
  public String getCaptchaTokenSeparator() {
    return captchaTokenSeparator;
  }
}
