package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
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
   * Renvoie un Token JWT permettant de générer des captcha
   * @return HttpHeaders
   */
  HttpHeaders generateCaptchaAccessKey();
}
