package com.ctoutweb.lalamiam.infra.core.model;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.ICoreRegisterFile;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.impl.CoreRegisterFileImpl;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.port.IProfessionalInscriptionInput;
import com.ctoutweb.lalamiam.infra.core.mapper.CoreRegisterFileMapper;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.model.auth.IRegisterFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public record ProfessionalRegisterInputImpl(RegisterProfessionalDto dto, String haspassword, List<IRegisterFile> professionalFiles) implements IProfessionalInscriptionInput {
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
  public List<ICoreRegisterFile> getProfessionalInscriptionDocuments() {
    return professionalFiles.stream().map(doc->new CoreRegisterFileImpl(doc.getRegisterFile(), doc.getFileSize(), doc.getFileExtension())).collect(Collectors.toList());
  }
}
