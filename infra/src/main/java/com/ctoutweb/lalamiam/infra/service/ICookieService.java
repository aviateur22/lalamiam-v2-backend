package com.ctoutweb.lalamiam.infra.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Gestion des cookies
 */
public interface ICookieService {
  /**
   * Suppression d'un cookie
   * @param name String - Nom du cookie
   * @return Cookie
   */
  public Cookie cancelCookie(String name);

  /**
   * Creation Cookie
   * @param name Sreing - Nom cookie
   * @param value String - Contenu du cookie
   * @return String - Données du cookie
   */
  public String generateCookie(String name, String value);

  /**
   * Recherche d'un cookie
   * @param request HttpServletRequest
   * @param name String - Nom du cooke
   * @return Cookie - Cookie de recherché
   */
  public Cookie findCookie(HttpServletRequest request, String name);
}
