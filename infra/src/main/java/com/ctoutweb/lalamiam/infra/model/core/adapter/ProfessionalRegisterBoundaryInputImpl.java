package com.ctoutweb.lalamiam.infra.model.core.adapter;

import com.ctoutweb.lalamiam.core.adapter.professionalInscription.IBoundariesAdapter.IBoundaryInputAdapter;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;

import java.util.List;

public record ProfessionalRegisterBoundaryInputImpl(RegisterProfessionalDto dto, String haspassword) implements IBoundaryInputAdapter {
  @Override
  public String getHashPassword() {
    return haspassword;
  }

  @Override
  public String getEmail() {
    return dto.email();
  }

  @Override
  public String getNickName() {
    return dto.nickname();
  }

  @Override
  public String getLastName() {
    return dto.lastName();
  }

  @Override
  public String getFirstName() {
    return dto.firstName();
  }

  @Override
  public String getPhone() {
    return dto.phone();
  }

  @Override
  public List<String> getDocuments() {
    return null;
  }
}
