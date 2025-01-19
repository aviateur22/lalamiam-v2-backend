package com.ctoutweb.lalamiam.infra.security.csrf;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.service.ICookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import java.util.UUID;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.*;

public class CustomCsrfTokenRepositoryImpl implements ICustomCsrfTokenRepository {
  private static final Logger LOGGER = LogManager.getLogger();
  private final ICookieService cookieService;
  private final Factory factory;

  public CustomCsrfTokenRepositoryImpl(ICookieService cookieService, Factory factory) {
    this.cookieService = cookieService;
    this.factory = factory;
  }

  @Override
  public CsrfToken loadHeaderToken(HttpServletRequest request) {
    String token = request.getHeader(POST_CSRF_TOKEN);
    return token != null ? factory.getCsrfTokenImpl(POST_CSRF_TOKEN, HEADER_CSRF_PARAM_NAME, token) : null;
  }

  @Override
  public CsrfToken loadCookieToken(HttpServletRequest request) {
    var cookie = cookieService.findCookie(request, COOKIE_CSRF_PARAM_NAME);

    if(cookie == null)
      return null;

    return factory.getCsrfTokenImpl(POST_CSRF_TOKEN, HEADER_CSRF_PARAM_NAME, cookie.getValue());
  }

  @Override
  public CsrfToken generateToken(HttpServletRequest request) {
    String token = UUID.randomUUID().toString();
    return new DefaultCsrfToken(POST_CSRF_TOKEN, COOKIE_CSRF_PARAM_NAME, token);
  }

  @Override
  public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
    if (token == null) {
      response.setHeader(POST_CSRF_TOKEN, "-");
      response.addCookie(cookieService.cancelCookie(COOKIE_CSRF_PARAM_NAME));
      return;
    }

    response.addHeader("Set-Cookie", cookieService.generateCookie(
            COOKIE_CSRF_PARAM_NAME,
            token.getToken(),
            CSRF_COOKIE_MAX_AGE));
    response.addHeader(POST_CSRF_TOKEN, token.getToken());

  }

  @Override
  public CsrfToken loadToken(HttpServletRequest request) {
    Cookie cookie = cookieService.findCookie(request, COOKIE_CSRF_PARAM_NAME);

    if(cookie == null)
      return null;

    return factory.getCsrfTokenImpl(POST_CSRF_TOKEN, COOKIE_CSRF_PARAM_NAME, cookie.getValue());
  }
}
