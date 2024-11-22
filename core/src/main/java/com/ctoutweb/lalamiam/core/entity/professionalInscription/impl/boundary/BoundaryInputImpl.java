package com.ctoutweb.lalamiam.core.entity.professionalInscription.impl.boundary;

import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;

import java.util.List;

public record BoundaryInputImpl(
        String hashPassword,
        String email,
        String name,
        String firstName,
        String phone,
        List<String> documents,
        String captchaResponseByUser,
        String hashOrDecrypteCaptchaResponse,
        CryptographicType cryptographicType,
        String capcthaToken,
        String captchaTokenSeparator

) implements IBoundaryInputAdapter {
  public static BoundaryInputImpl getBoundaryInputImpl(
          String hashPassword,
          String email,
          String name,
          String firstName,
          String phone,
          List<String> documents,
          String captchaResponseByUser,
          String hashOrDecrypteCaptchaResponse,
          CryptographicType cryptographicType,
          String capcthaToken,
          String captchaTokenSeparator
  ) {
    return new BoundaryInputImpl(
            hashPassword,
            email,
            name,
            firstName,
            phone,
            documents,
            captchaResponseByUser,
            hashOrDecrypteCaptchaResponse,
            cryptographicType,
            capcthaToken,
            captchaTokenSeparator
    );
  }

  @Override
  public String getHashPassword() {
    return hashPassword();
  }

  @Override
  public String getEmail() {
    return email();
  }

  @Override
  public String getName() {
    return name();
  }

  @Override
  public String getFirstName() {
    return firstName();
  }

  @Override
  public String getPhone() {
    return phone();
  }

  @Override
  public List<String> getDocuments() {
    return documents;
  }


  @Override
  public String getCaptchaResponseByUser() {
    return captchaResponseByUser;
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
