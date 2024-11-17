package entity.professionalInscription.impl.boundary;

import adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;
import entity.cryptographic.CryptographicType;

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
        CryptographicType cryptographicType

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
          CryptographicType cryptographicType
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
            cryptographicType
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


}
