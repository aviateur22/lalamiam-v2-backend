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
    ReflectionTestUtils.setField(cryptoService, "cryptoName", "AES");
    ReflectionTestUtils.setField(cryptoService, "cryptoKey", "hjdfjkdhldhljdfldmùmddùPQdùmslqdfmsqnflnfclqdsfldqjfcbdqcnlfdqmfmsfùmsqdjmjffcsqfjqsdjqsqsm");
    ReflectionTestUtils.setField(cryptoService, "cryptoSalt", "8E2EDB261622042C4BA537F970B842A22C48836BFBC9BEC8756DB400A41E41EAE2F628DE3F1F0A690AA57705CE62C5E9BCED67C8D5C22C17D735D0C137D7CC81");
    ReflectionTestUtils.setField(cryptoService, "cryptoSecretkeyAlgo", "PBKDF2WithHmacSHA256");
    ReflectionTestUtils.setField(cryptoService, "cryptoAlgo", "AES/CBC/PKCS5Padding");
    captchaGeneration = new CaptchaGenerationImpl(messageService, tokenRepository, cryptoService, factory);
    captchaTextStrategy = new CaptchaTextStrategyImpl(captchaGeneration);
  }

  @Test
  public void generate_captcha_text_type() throws IOException {
    /**
     * given
     */
    TokenEntity saveCaptchaToken = new TokenEntity();
    saveCaptchaToken.setId(1L);
    saveCaptchaToken.setIvKey("3pjts9RbBStgasYj2HSdOQ==");
    saveCaptchaToken.setCryptographyType(CryptographyType.HASH.toString());
    saveCaptchaToken.setCryptographyText("jfdldqslksqmlsmsmsqmdqsmldqsmkldsqmlkdqskmlù");

    ReflectionTestUtils.setField(captchaGeneration, "zoneId", "Europe/Paris");
    ReflectionTestUtils.setField(captchaGeneration, "captchaIvKey", "+DYq3LSUul8V4/zCnvRmgQ==");

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
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64().getBase64Format());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64().getMimeType());
    Assertions.assertNotNull(captcha.getCaptchaResponseIdEncrypt());
    Assertions.assertEquals("CzY3Q89XFanTbxlIpTvpaw", captcha.getCaptchaResponseIdEncrypt());
    Assertions.assertEquals(captchaTile, captcha.getCaptchaTitle());

  }
}
