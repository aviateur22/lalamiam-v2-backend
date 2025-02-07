package com.ctoutweb.lalamiam.infra.core.mapper;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.ICoreRegisterFile;
import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.impl.CoreRegisterFileImpl;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class CoreRegisterFileMapper {
  public ICoreRegisterFile map(InputStream documentFile, Long fileSize) {
    return new CoreRegisterFileImpl(documentFile,fileSize);
  }
}
