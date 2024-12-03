package com.ctoutweb.lalamiam.infra.model;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.impl.CaptchaGenerationImpl;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.impl.CaptchaTextStrategyImpl;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.service.impl.CryptographiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CaptchaTextStrategyImplTest {
  @Mock
  IMessageService messageService;
  @Mock
  ITokenRepository tokenRepository;
  CryptographiceServiceImpl cryptoService;
  CaptchaGenerationImpl captchaGeneration;
  Factory factory = new Factory();
  CaptchaTextStrategyImpl captchaTextStrategy;

  @BeforeEach
  public void init()  {
    MockitoAnnotations.openMocks(this);

    cryptoService = new CryptographiceServiceImpl(new BCryptPasswordEncoder());
    captchaGeneration = new CaptchaGenerationImpl(messageService, tokenRepository, cryptoService, factory);
    captchaTextStrategy = new CaptchaTextStrategyImpl(captchaGeneration, factory);
  }

  @Test
  public void generate_captcha_text_type() throws IOException {
    /**
     * given
     */
    TokenEntity saveCaptchaToken = new TokenEntity();
    saveCaptchaToken.setId(1L);
    saveCaptchaToken.setIvKey(null);
    saveCaptchaToken.setCryptographyType(CryptographyType.HASH.toString());
    saveCaptchaToken.setCryptographyText("jfdldqslksqmlsmsmsqmdqsmldqsmkldsqmlkdqskmlù");

    ReflectionTestUtils.setField(captchaGeneration, "zoneId", "Europe/Paris");
    String captchaTile = "Recopier le mot";
    when(messageService.getMessage("captcha.text.title")).thenReturn(captchaTile);
    when(tokenRepository.save(any())).thenReturn(saveCaptchaToken);


    /**
     * when
     */
    ICaptcha captcha = captchaTextStrategy.generateCaptcha();

    /**
     * then
     */
    Assertions.assertNotNull(captcha.getCaptchaTitle());
    Assertions.assertNotNull(captcha.getCaptchaQuestionBase64());
    Assertions.assertNotNull(captcha.getCaptchaQuestionBase64().getBase64Format());
    Assertions.assertNotNull(captcha.getCaptchaQuestionBase64().getMimeType());
    Assertions.assertNotNull(captcha.getResponseId());
    Assertions.assertEquals(1L, captcha.getResponseId());
    Assertions.assertEquals(captchaTile, captcha.getCaptchaTitle());

  }
}
