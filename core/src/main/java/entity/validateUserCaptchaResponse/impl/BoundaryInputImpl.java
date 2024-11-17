package entity.validateUserCaptchaResponse.impl;

import adapter.validateUserCaptchaResponse.IBoundariesAdapter.IBoundaryInputAdapter;
import entity.cryptographic.CryptographicType;

public record BoundaryInputImpl(
        String clientResponse,
        String hashOrDecrypteCaptchaResponse,
        CryptographicType cryptographicType
) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(
          String captchaResponseByUser,
          String hashOrDecrypteCaptchaResponse,
          CryptographicType cryptographicType
  ) {
    return new BoundaryInputImpl(captchaResponseByUser, hashOrDecrypteCaptchaResponse, cryptographicType);
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
}
