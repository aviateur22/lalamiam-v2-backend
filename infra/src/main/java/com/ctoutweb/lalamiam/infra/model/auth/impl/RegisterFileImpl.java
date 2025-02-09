package com.ctoutweb.lalamiam.infra.model.auth.impl;

import com.ctoutweb.lalamiam.infra.model.auth.IRegisterFile;

import java.io.InputStream;

public record RegisterFileImpl(InputStream registerFile, Long fileSize, String fileExtension) implements IRegisterFile {
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
