package com.ctoutweb.lalamiam.infra.model;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaImage;
import com.ctoutweb.lalamiam.infra.model.captcha.impl.CaptchaGenerationImpl;
import com.ctoutweb.lalamiam.infra.model.captcha.impl.CaptchaImageImpl;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.mapper.CaptchaImageMapper;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.CaptchaImageEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class CaptchaGenerationTest {
  @Mock
  IMessageService messageService;

  @Mock
  ITokenRepository tokenRepository;

  @Mock
  ICryptoService cryptoService;

  Factory factory;

  CaptchaGenerationImpl capatchaGeneration;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    factory = new Factory();

    capatchaGeneration = new CaptchaGenerationImpl(messageService, tokenRepository, cryptoService, factory);
  }
  @Test
  public void should_create_captcha_calcul_type() {
    /**
     * given
     */
    when(messageService.getMessage("captcha.calcul.title")).thenReturn("captcha");

    /**
     * when
     */
    capatchaGeneration.captchaTypeCalcul();

    /**
     * then
     */
    Assertions.assertNotNull(capatchaGeneration.getCaptchaQuestion());
    Assertions.assertNotNull(capatchaGeneration.getCaptchaTitle());
    Assertions.assertNotNull(capatchaGeneration.getCaptchaResponse());



  }

  @Test
  public void should_create_captcha_text_type()  {
    /**
     * given
     */
    when(messageService.getMessage("captcha.text.title")).thenReturn("captcha");

    /**
     * when
     */
    capatchaGeneration.captchaTypeText();

    /**
     * then
     */
    Assertions.assertNotNull(capatchaGeneration.getCaptchaQuestion());
    Assertions.assertNotNull(capatchaGeneration.getCaptchaTitle());
    Assertions.assertNotNull(capatchaGeneration.getCaptchaResponse());
    Assertions.assertEquals(capatchaGeneration.getCaptchaQuestion(), capatchaGeneration.getCaptchaResponse());



  }

  @Test
  public void should_create_captcha_image_type() {
    /**
     * given
     */

    CaptchaImageMapper captchaImageMapper = new CaptchaImageMapper(factory);

    List<ICaptchaImage> captchaImages = generateCaptchaImages().stream().map(captchaImageMapper::map).toList();

    when(messageService.getMessage("captcha.image.title")).thenReturn("captcha");


    /**
     * when
     */
    capatchaGeneration.captchaTypeImage(captchaImages, getCaptchaImageFiles());

    /**
     * then
     */
    Assertions.assertNull(capatchaGeneration.getCaptchaQuestion());
    Assertions.assertNotNull(capatchaGeneration.getCaptchaTitle());
    Assertions.assertNotNull(capatchaGeneration.getCaptchaResponse());
  }

  /**
   * Récupération des images disponible dans le folder image/captcha
   * pour les captchas de type image
   * @return List<File> - Liste des images disponible
   */
  public List<File> getCaptchaImageFiles() {
    String folderPath = "image/captcha"; // Update path to match module structure
    File folder = new File(folderPath);

    if (folder.exists() && folder.isDirectory()) {

      File[] filesArray = folder.listFiles((dir, name) -> {
        String lowercaseName = name.toLowerCase();
        return lowercaseName.endsWith(".jpg") ||
                lowercaseName.endsWith(".jpeg") ||
                lowercaseName.endsWith(".png");
      });

      return Arrays.stream(filesArray).toList();
    }
    return List.of();
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
