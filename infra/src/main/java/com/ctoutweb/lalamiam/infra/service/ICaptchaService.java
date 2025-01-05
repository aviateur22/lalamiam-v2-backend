package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

/**
 * Gestion des captchas
 */
public interface ICaptchaService {

  /**
   * Renvoi un captacha généré en mode Random
   * @return ICaptcha
   */
  ICaptcha generateRandomCapatcha(HttpServletRequest request, HttpServletResponse response) throws IOException;

  /**
   * Validation de la réponse a un captcha
   * @param captchaResponse IUserCaptchaResponse
   * @return Boolean
   */
  Boolean isCaptchaReponseValid(IUserCaptchaResponse captchaResponse);

  /**
   * Renvoie un Token JWT permettant de générer des captcha
   * @return HttpHeaders
   */
  HttpHeaders generateCaptchaAccessKey();

  /**
   * Renvoie un Token JWT permettant de générer des captcha
   * Headers en paramètre quand des données sont déja presente
   * @param httpHeaders HttpHeaders
   * @return HttpHeaders
   */
  HttpHeaders generateCaptchaAccessKey(HttpHeaders httpHeaders);
}
