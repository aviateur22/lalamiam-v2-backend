package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

/**
 * Service permettant de gérer la config de l'applicvation
 */
public interface ISecurityService {
  /**
   * Génération d'un token CSRF
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   */
  public void generateCsrf(HttpServletRequest request, HttpServletResponse response);

  /**
   * Génération d'une clé permettant de générer des Token CSRF
   * @return HttpHeaders
   */
  public HttpHeaders generateCsrfAccessKey();

  /**
   * Génération d'une clé permettant de générer des captcha
   * @return HttpHeaders
   */
  public HttpHeaders generateCaptchaAccessKey();

  /**
   * Génération des données captcha
   * @return ICaptcha
   */
  public ICaptcha generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException;

  /**
   * Vérification captcha response
   * @param captchaResponse IUserCaptchaResponse
   * @return boolean
   */
  public boolean isUserCaptchaResponseValid(IUserCaptchaResponse captchaResponse);
}