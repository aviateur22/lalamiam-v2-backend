package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.entity.common.ITokenToValidate;
import com.ctoutweb.lalamiam.core.entity.cryptographic.CryptographicType;
import com.ctoutweb.lalamiam.core.entity.cryptographic.ICryptography.ICryptographySaveResult;
import com.ctoutweb.lalamiam.core.provider.ICryptographicService;
import com.ctoutweb.lalamiam.infra.service.ITextHashService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CryptographiceServiceImpl implements ICryptographicService, ITextHashService {
  private final PasswordEncoder passwordEncoder;

  public CryptographiceServiceImpl(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public ICryptographySaveResult cryptographyTextAndSave(CryptographicType cryptographicType, String plainText, LocalDateTime limitValidity) {
    return null;
  }

  @Override
  public Boolean isHashValid(ITokenToValidate tokenToValidate) {
    return null;
  }

  @Override
  public String hashText(String textToHash) {
    return passwordEncoder.encode(textToHash);
  }

  @Override
  public boolean isHashValid(String plainText, String hash) {
    return passwordEncoder.matches(plainText, hash);
  }
}
