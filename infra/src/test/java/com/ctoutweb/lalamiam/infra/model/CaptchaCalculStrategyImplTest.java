package com.ctoutweb.lalamiam.infra.model;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.impl.CaptchaGenerationImpl;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.impl.CaptchaCalculStrategyImpl;
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

public class CaptchaCalculStrategyImplTest {
  @Mock
  IMessageService messageService;
  @Mock
  ITokenRepository tokenRepository;
  CryptographiceServiceImpl cryptoService;
  CaptchaGenerationImpl captchaGeneration;
  Factory factory = new Factory();
  CaptchaCalculStrategyImpl captchaCalculStrategy;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);

    cryptoService = new CryptographiceServiceImpl(new BCryptPasswordEncoder());
    captchaGeneration = new CaptchaGenerationImpl(messageService, tokenRepository, cryptoService, factory);
    captchaCalculStrategy = new CaptchaCalculStrategyImpl(captchaGeneration);
  }

  @Test
  public void generate_captcha_calcul_type() throws IOException {
    /**
     * given
     */
    TokenEntity saveCaptchaToken = new TokenEntity();
    saveCaptchaToken.setId(1L);
    saveCaptchaToken.setIvKey(null);
    saveCaptchaToken.setCryptographyType(CryptographyType.HASH.toString());
    saveCaptchaToken.setCryptographyText("jfdldqslksqmlsmsmsqmdqsmldqsmkldsqmlkdqskmlù");

    String captchaTile = "Trouver le bon resulta";

    ReflectionTestUtils.setField(captchaGeneration, "zoneId", "Europe/Paris");
    ReflectionTestUtils.setField(captchaGeneration, "captchaIvKey", "+DYq3LSUul8V4/zCnvRmgQ==");
    ReflectionTestUtils.setField(cryptoService,"cryptoAlgo", "AES/CBC/PKCS5Padding");
    ReflectionTestUtils.setField(cryptoService, "cryptoSecretkeyAlgo", "PBKDF2WithHmacSHA256");
    ReflectionTestUtils.setField(cryptoService, "cryptoKey", "dfjdfjdfjdfmdsfmdfmdfdjfdjfdfdsmfdsjfdfmdsfjdsfmsjjj");
    ReflectionTestUtils.setField(cryptoService, "cryptoSalt", "6E4EDB261622042C4BA537F970B842A22C48836BFBC9BEC7890DB400A41E41EAE2F567DE3F1F0A690AA57705CE62C5E9BCED67C8D5C22C17D735D0C137D7CC91");
    ReflectionTestUtils.setField(cryptoService, "cryptoName", "AES");
    when(messageService.getMessage("captcha.calcul.title")).thenReturn(captchaTile);
    when(tokenRepository.save(any())).thenReturn(saveCaptchaToken);


    /**
     * when
     */
    ICaptcha captcha = captchaCalculStrategy.generateCaptcha();

    /**
     * then
     */
    Assertions.assertNotNull(captcha.getCaptchaTitle());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64().getBase64Format());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64().getMimeType());
    Assertions.assertNotNull(captcha.getCaptchaResponseIdEncrypt());
    Assertions.assertEquals("xYx7LtKKzPJST6TTqfg8Aw", captcha.getCaptchaResponseIdEncrypt());
    Assertions.assertEquals(captchaTile, captcha.getCaptchaTitle());

  }
}
