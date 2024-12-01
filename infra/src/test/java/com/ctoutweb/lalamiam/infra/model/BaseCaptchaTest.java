package com.ctoutweb.lalamiam.infra.model;

import com.ctoutweb.lalamiam.infra.model.captcha.BaseCapatcha;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.CaptchaImageEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.when;

public class BaseCaptchaTest {
  @Mock
  IMessageService messageService;

  @Mock
  ITokenRepository tokenRepository;

  @Mock
  ICryptoService cryptoService;

  BaseCapatcha baseCapatcha;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    baseCapatcha = new BaseCapatcha(messageService, tokenRepository, cryptoService);
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
    baseCapatcha.captchaTypeCalcul();

    /**
     * then
     */
    Assertions.assertNotNull(baseCapatcha.getCaptchaQuestion());
    Assertions.assertNotNull(baseCapatcha.getCaptchaTitle());
    Assertions.assertNotNull(baseCapatcha.getCaptchaResponse());



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
    baseCapatcha.captchaTypeText();

    /**
     * then
     */
    Assertions.assertNotNull(baseCapatcha.getCaptchaQuestion());
    Assertions.assertNotNull(baseCapatcha.getCaptchaTitle());
    Assertions.assertNotNull(baseCapatcha.getCaptchaResponse());
    Assertions.assertEquals(baseCapatcha.getCaptchaQuestion(), baseCapatcha.getCaptchaResponse());



  }

  @Test
  public void should_create_captcha_image_type() {
    /**
     * given
     */

    CaptchaImageEntity captchaImage = new CaptchaImageEntity();
    String fakePath = "fake/path";
    String fakeResponse = "fakeResponse";

    captchaImage.setPath(fakePath);
    captchaImage.setResponse(fakeResponse);
    List<CaptchaImageEntity> captchaImageEntities = List.of(captchaImage);

    when(messageService.getMessage("captcha.image.title")).thenReturn("captcha");


    /**
     * when
     */
    baseCapatcha.captchaTypeImage(captchaImageEntities);

    /**
     * then
     */
    Assertions.assertNull(baseCapatcha.getCaptchaQuestion());
    Assertions.assertNotNull(baseCapatcha.getImagePath());
    Assertions.assertNotNull(baseCapatcha.getCaptchaTitle());
    Assertions.assertNotNull(baseCapatcha.getCaptchaResponse());
    Assertions.assertEquals(fakePath, baseCapatcha.getImagePath());
    Assertions.assertEquals(fakeResponse, baseCapatcha.getCaptchaResponse());



  }



}
