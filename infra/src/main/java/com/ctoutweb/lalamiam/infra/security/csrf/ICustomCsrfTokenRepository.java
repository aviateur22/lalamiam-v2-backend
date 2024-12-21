package com.ctoutweb.lalamiam.infra.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;

public interface ICustomCsrfTokenRepository extends CsrfTokenRepository {
  /**
   * Chargement CSRF Token provenant d'un formulaire
   * @param request HttpServletRequest
   * @return HeaderCsrfFormTokenImpl
   */
  public CsrfToken loadHeaderToken(HttpServletRequest request);

  /**
   * Récupération du token CSRF depuis le cookie
   * @param request HttpServletRequest
   * @return CsrfToken
   */
  public CsrfToken loadCookieToken(HttpServletRequest request);
}
