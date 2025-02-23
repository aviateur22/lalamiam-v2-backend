package com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.impl;

import com.ctoutweb.lalamiam.core.useCase.professionalInscription.entity.ICoreRegisterFile;

import java.io.InputStream;

public record CoreRegisterFileImpl(InputStream registerFile, Long fileSize, String fileExtension) implements ICoreRegisterFile {
  @Override
  public InputStream getRegisterFile() {
    return registerFile;
  }

  @Override
  public Long getFileSize() {
    return fileSize;
  }

  @Override
  public String getFileExtension() {
    return fileExtension;
  }
}
