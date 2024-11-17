package entity.clientInscription.impl.boundaries;

import adapter.clientInscription.boundary.IBoundariesAdapter.IBoundaryInputAdapter;
import entity.cryptographic.CryptographicType;

public record BoundaryInputImpl(
        String hashPassword,
        String email,
        String name,
        String userCaptchaResponse,
        String hashOrDecrypteCaptchaResponse,
        CryptographicType cryptographicType
) implements IBoundaryInputAdapter {

  public static BoundaryInputImpl getBoundaryInputImpl(
          String hashPassword,
          String email,
          String name,
          String userCaptchaResponse,
          String hashOrDecrypteCaptchaResponse,
          CryptographicType cryptographicType
  ) {
    return new BoundaryInputImpl(
            hashPassword,
            email,
            name,
            userCaptchaResponse,
            hashOrDecrypteCaptchaResponse,
            cryptographicType
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
  public String getName() {
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

}
