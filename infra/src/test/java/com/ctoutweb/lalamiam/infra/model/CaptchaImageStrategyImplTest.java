package com.ctoutweb.lalamiam.infra.model;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.impl.CaptchaGenerationImpl;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.impl.CaptchaImageStrategyImpl;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.mapper.CaptchaImageMapper;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ICaptchaImageRepository;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.CaptchaImageEntity;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CaptchaImageStrategyImplTest {
  @Mock
  IMessageService messageService;
  @Mock
  ITokenRepository tokenRepository;
  @Mock
  ICaptchaImageRepository captchaImageRepository;
  CryptographiceServiceImpl cryptoService;
  CaptchaGenerationImpl captchaGeneration;
  Factory factory = new Factory();
  CaptchaImageStrategyImpl captchaImageStrategy;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);

    cryptoService = new CryptographiceServiceImpl(new BCryptPasswordEncoder());
    ReflectionTestUtils.setField(cryptoService, "cryptoName", "AES");
    ReflectionTestUtils.setField(cryptoService, "cryptoKey", "hjdfjkdhldhljdfldmùmddùPQdùmslqdfmsqnflnfclqdsfldqjfcbdqcnlfdqmfmsfùmsqdjmjffcsqfjqsdjqsqsm");
    ReflectionTestUtils.setField(cryptoService, "cryptoSalt", "8E2EDB261622042C4BA537F970B842A22C48836BFBC9BEC8756DB400A41E41EAE2F628DE3F1F0A690AA57705CE62C5E9BCED67C8D5C22C17D735D0C137D7CC81");
    ReflectionTestUtils.setField(cryptoService, "cryptoSecretkeyAlgo", "PBKDF2WithHmacSHA256");
    ReflectionTestUtils.setField(cryptoService, "cryptoAlgo", "AES/CBC/PKCS5Padding");

    CaptchaImageMapper captchaImageMapper = new CaptchaImageMapper(factory);
    captchaGeneration = new CaptchaGenerationImpl(messageService, tokenRepository, cryptoService, factory);
    captchaImageStrategy = new CaptchaImageStrategyImpl(captchaGeneration, captchaImageRepository, captchaImageMapper, factory);
  }

  @Test
  public void generate_captcha_image_type() throws IOException {
    /**
     * given
     */
    // Generation d'un liste de données sur les images captcha disponible
    List<CaptchaImageEntity>captchaImages = generateCaptchaImages();

    TokenEntity saveCaptchaToken = new TokenEntity();
    saveCaptchaToken.setId(1L);
    saveCaptchaToken.setIvKey("tzko/r9RK384RGo0FhR+cg==");
    saveCaptchaToken.setCryptographyType(CryptographyType.ENCRYPT.toString());
    saveCaptchaToken.setCryptographyText("jfdldqslksqmlsmsmsqmdqsmldqsmkldsqmlkdqskmlù");

    String captchaTile = "Quel est cette image";

    ReflectionTestUtils.setField(captchaGeneration, "zoneId", "Europe/Paris");
    ReflectionTestUtils.setField(captchaImageStrategy, "captchaFolderImage", "image/captcha");
    ReflectionTestUtils.setField(captchaGeneration, "captchaIvKey", "+DYq3LSUul8V4/zCnvRmgQ==");
    when(messageService.getMessage("captcha.image.title")).thenReturn(captchaTile);
    when(captchaImageRepository.findAll()).thenReturn(captchaImages);
    when(tokenRepository.save(any())).thenReturn(saveCaptchaToken);

    /**
     * when
     */
    ICaptcha captcha = captchaImageStrategy.generateCaptcha();

    /**
     * then
     */
    Assertions.assertNotNull(captcha.getCaptchaTitle());
    Assertions.assertNotNull(captcha.getCaptchaResponseIdEncrypt());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64().getMimeType());
    Assertions.assertNotNull(captcha.getCaptchaQuestionImageBase64().getBase64Format());

  }

  private List<CaptchaImageEntity> generateCaptchaImages() {
    String folderPath = "image/captcha"; // Update path to match module structure
    File folder = new File(folderPath);
    List<CaptchaImageEntity> captchaImages = new ArrayList<>();
    if (folder.exists() && folder.isDirectory()) {
      File[] filesArray = folder.listFiles((dir, name) -> {
        String lowercaseName = name.toLowerCase();
        return lowercaseName.endsWith(".jpg") ||
                lowercaseName.endsWith(".jpeg") ||
                lowercaseName.endsWith(".png");
      });

      for(File file:filesArray) {
        CaptchaImageEntity captchaImage = new CaptchaImageEntity();
        captchaImage.setName(file.getName());
        captchaImage.setPath("path");
        captchaImage.setResponse("Response");
        captchaImage.setId(1L);
        captchaImages.add(captchaImage);
      }
    }
    return captchaImages;
  }


}
