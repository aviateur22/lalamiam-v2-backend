package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;

import java.io.File;
import java.util.List;

public record ProfessionalRegisterInputImpl(RegisterProfessionalDto dto, String haspassword) implements IProfessionalInscriptionInput {
  @Override
  public String getHashPassword() {
    return haspassword;
  }

  @Override
  public String getEmail() {
    return dto.getEmail();
  }

  @Override
  public String getNickName() {
    return dto.getNickname();
  }

  @Override
  public String getLastName() {
    return dto.getLastName();
  }

  @Override
  public String getFirstName() {
    return dto.getFirstName();
  }

  @Override
  public String getPhone() {
    return dto.getPhone();
  }

  @Override
  public List<File> getProfessionalInscriptionDocuments() {
    return null;
  }
}
