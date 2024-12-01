package com.ctoutweb.lalamiam.infra.model.captcha.strategy.impl;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.BaseCapatcha;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.ICaptchaStrategy;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CaptchaCalculImpl implements ICaptchaStrategy {
  private final IMessageService messageService;
  private final Factory factory;
  private final CaptchaType captchaType = CaptchaType.CALCUL;
  private final CryptographyType cryptoType = CryptographyType.HASH;
  private final ITokenRepository tokenRepository;
  private final ICryptoService cryptoService;

  public CaptchaCalculImpl(IMessageService messageService, Factory factory, ITokenRepository tokenRepository, ICryptoService cryptoService) {
    this.messageService = messageService;
    this.factory = factory;
    this.tokenRepository = tokenRepository;
    this.cryptoService = cryptoService;
  }

  @Override
  public ICaptcha generateCaptcha() throws IOException {

    BaseCapatcha capatcha = factory.getImpl(messageService, tokenRepository, cryptoService)
            .generate(captchaType)
            .cryptographyAndSaveResponse(cryptoType)
            .convertCaptchaQuestionToBase64Image();
    return null;
  }
}
