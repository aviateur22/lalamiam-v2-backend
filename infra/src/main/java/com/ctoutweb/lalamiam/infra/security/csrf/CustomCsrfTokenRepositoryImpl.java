package com.ctoutweb.lalamiam.infra.security.csrf;

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

  public CustomCsrfTokenRepositoryImpl(ICookieService cookieService) {
    this.cookieService = cookieService;
  }

  @Override
  public CsrfToken loadHeaderToken(HttpServletRequest request) {
    String token = request.getHeader(FORM_CSRF_TOKEN);
    return token != null ? new CsrfHeaderTokenImpl(FORM_CSRF_TOKEN, HEADER_CSRF_PARAM_NAME, token) : null;
  }

  @Override
  public CsrfToken generateToken(HttpServletRequest request) {
    String token = UUID.randomUUID().toString();
    return new DefaultCsrfToken(X_CSRF_TOKEN, COOKIE_CSRF_PARAM_NAME, token);
  }

  @Override
  public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
    if (token == null) {
      response.setHeader(X_CSRF_TOKEN, "-");
      response.addCookie(cookieService.cancelCookie(COOKIE_CSRF_PARAM_NAME));
      return;
    }

    response.addHeader("Set-Cookie", cookieService.generateCookie(COOKIE_CSRF_PARAM_NAME, token.getToken()));
    response.addHeader(FORM_CSRF_TOKEN, token.getToken());

  }

  @Override
  public CsrfToken loadToken(HttpServletRequest request) {
    Cookie cookie = cookieService.findCookie(request, COOKIE_CSRF_PARAM_NAME);

    if(cookie == null)
      return null;

    return new CsrfCookieTokenImpl(X_CSRF_TOKEN, COOKIE_CSRF_PARAM_NAME, cookie.getValue());
  }
}
