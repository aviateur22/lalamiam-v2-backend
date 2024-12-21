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
        List<String> documents

) implements IBoundaryInputAdapter {
  public static BoundaryInputImpl getBoundaryInputImpl(
          String hashPassword,
          String email,
          String name,
          String firstName,
          String phone,
          List<String> documents
  ) {
    return new BoundaryInputImpl(
            hashPassword,
            email,
            name,
            firstName,
            phone,
            documents
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
  public String getUserName() {
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
}
