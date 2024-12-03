package com.ctoutweb.lalamiam.infra.model.captcha;

import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Abastraction des étapes de creation d'un captcha
 */
public interface ICaptchaGeneration {

  /**
   * Récupération titre du captcha
   * @return String
   */
  public String getCaptchaTitle();

  /**
   * Données sur l'image captcha
   * @return IImageBase64
   */
  public IImageBase64 getCaptchaQuestionBase64();

  /**
   * Id de la réponse attendu en base
   * @return Long
   */
  public Long getCaptchaResponseId();

  /**
   * Validité du captcha
   * @return ZonedDateTime
   */
  public ZonedDateTime getValidUntil();

  /**
   * Génération des donées captcha pour un captcha type calcul ou text
   * @param captchaType CaptchaType
   * @return IBaseCaptcha
   */
  ICaptchaGeneration generate(CaptchaType captchaType);

  /**
   * Génération des donées captcha pour un captcha type Image
   * @param captchaImages List<ICaptchaImage>
   * @return IBaseCaptcha
   */
  ICaptchaGeneration generate(List<ICaptchaImage> captchaImages, List<File> captchaImageFiles);

  /**
   * Cryptographie de la réponse au captcha et sauvegarde en base
   * @param cryptographyType CryptographyType - Type de cryptographie a utiliser
   * @return IBaseCaptcha
   */
  ICaptchaGeneration cryptographyAndSaveResponse(CryptographyType cryptographyType);

  /**
   * Convertion de la question en image Base 64
   * @return IBaseCaptcha
   * @throws IOException
   */
  ICaptchaGeneration convertCaptchaQuestionToBase64Image() throws IOException;

}
