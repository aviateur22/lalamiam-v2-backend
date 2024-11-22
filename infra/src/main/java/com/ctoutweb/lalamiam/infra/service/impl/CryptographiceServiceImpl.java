package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.entity.common.ITokenToValidate;
import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CryptographiceServiceImpl implements ICryptographicService {
  @Override
  public ICryptography.ICryptographySaveResult cryptographyTextAndSave(CryptographicType cryptographicType, String plainText, LocalDateTime limitValidity) {
    return null;
  }

  @Override
  public Boolean isHashValid(ITokenToValidate tokenToValidate) {
    return null;
  }
}
