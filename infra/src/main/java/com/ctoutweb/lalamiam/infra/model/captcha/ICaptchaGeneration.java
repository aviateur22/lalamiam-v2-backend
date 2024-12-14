package com.ctoutweb.lalamiam.infra.model.captcha;

import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Abastraction des étapes de creation d'un captcha
 */
public interface ICaptchaGeneration {
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

  /**
   * Récupération des données Captcha
   * @return ICaptcha
   */
  ICaptcha getCaptcha();

}
