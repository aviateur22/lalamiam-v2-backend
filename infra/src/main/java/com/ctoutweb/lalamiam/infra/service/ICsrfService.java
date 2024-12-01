package com.ctoutweb.lalamiam.infra.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

/**
 * Gestion des token CSRF
 */
public interface ICsrfService {

  /**
   * Génération d'un token CSRF
   * @param request HttpServletRequest
   * @Param response HttpServletResponse
   */
  void generateCsrf(HttpServletRequest request, HttpServletResponse response);

  /**
   * Génération d'un token permettant de créer des token CSRF
   * @return HttpHeader
   */
  public HttpHeaders generateCsrfAccessKey();

}
