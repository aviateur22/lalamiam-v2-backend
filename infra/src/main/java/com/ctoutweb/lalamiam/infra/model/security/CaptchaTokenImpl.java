package com.ctoutweb.lalamiam.infra.model.security;

import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;

import java.util.Base64;

public class CaptchaTokenImpl implements ICaptchaToken {
  private final ICryptoService cryptoService;
  private final TokenEntity captchaResponseEntity;
  private final String captchaResponseByUser;
  private CryptographyType cryptographyType;


  public CaptchaTokenImpl(ICryptoService cryptoService, TokenEntity captchaResponseEntity, String captchaResponseByUser) {
    this.cryptoService = cryptoService;
    this.captchaResponseEntity = captchaResponseEntity;
    this.captchaResponseByUser = captchaResponseByUser;
  }

  @Override
  public ICaptchaToken getCryptographicType() {
    String typeName = this.captchaResponseEntity.getCryptographyType();

    for(CryptographyType cryptographyTypeAvail: CryptographyType.values()) {
      if(cryptographyTypeAvail.getName().toLowerCase().equals(typeName.toLowerCase())) {
        this.cryptographyType = cryptographyTypeAvail;
        break;
      }
    }
    return this;
  }
  @Override
  public Boolean isCaptchaResponseValid() {
    return switch (this.cryptographyType) {
      case HASH -> areHashCaptchaResponseAndClientResponseValid();
      case ENCRYPT -> areDecryptCaptchaResponseAndClientResponseValid();
    };
  }

  /**
   * Validation de la réponse client à partir d'une réponse captcha hashée
   * @return booelan
   */
  public boolean areHashCaptchaResponseAndClientResponseValid() {
    return cryptoService.isHashValid(this.captchaResponseByUser, captchaResponseEntity.getCryptographyText());
  }

  /**
   * Validation de réponse client à partir d'une réponse captcha chiffrée
   * True si la réponse du captcha se trouve dans la réponse client
   * @return boolean
   */
  public boolean areDecryptCaptchaResponseAndClientResponseValid() {
    byte[] iv = Base64.getDecoder().decode(this.captchaResponseEntity.getIvKey().getBytes());

    String decrypteCaptchaResponse = cryptoService.decrypt(
            this.captchaResponseEntity.getCryptographyText(),iv);

    return this.captchaResponseByUser.contains(decrypteCaptchaResponse);
  }
}
