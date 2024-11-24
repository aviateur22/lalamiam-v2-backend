package com.ctoutweb.lalamiam.core.entity.clientInscription.impl.boundaries;

import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;

public record BoundaryInputImpl(
        String hashPassword,
        String email,
        String name,
        String userCaptchaResponse,
        String hashOrDecrypteCaptchaResponse,
        CryptographicType cryptographicType,
        String capcthaToken,
        String captchaTokenSeparator
) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(
          String hashPassword,
          String email,
          String name,
          String userCaptchaResponse,
          String hashOrDecrypteCaptchaResponse,
          CryptographicType cryptographicType,
          String capcthaToken,
          String captchaTokenSeparator
  ) {
    return new BoundaryInputImpl(
            hashPassword,
            email,
            name,
            userCaptchaResponse,
            hashOrDecrypteCaptchaResponse,
            cryptographicType,
            capcthaToken,
            captchaTokenSeparator
    );
  }

  @Override
  public String getHashPassword() {
    return hashPassword;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getUserName() {
    return name;
  }

  @Override
  public String getCaptchaResponseByUser() {
    return userCaptchaResponse;
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
    return capcthaToken;
  }

  @Override
  public String getCaptchaTokenSeparator() {
    return captchaTokenSeparator;
  }

}
