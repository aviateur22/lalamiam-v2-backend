package com.ctoutweb.lalamiam.infra.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

/**
 * Service permettant de gérer la config de l'applicvation
 */
public interface IConfigurationService {
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
}
