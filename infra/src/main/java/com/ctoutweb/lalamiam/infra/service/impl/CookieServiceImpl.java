package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.service.ICookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource({"classpath:application.properties"})
public class CookieServiceImpl implements ICookieService {
  private static final Logger LOGGER = LogManager.getLogger();
  @Value("${cookie.domain}")
  String cookieDomain;
  @Value("${cookie.secure}")
  boolean isCookieSecure;
  @Value("${cookie.samesite}")
  String cookieSameSite;
  @Value("${cookie.path}")
  String cookiePath;
  @Value("${cookie.is.httponly}")
  boolean isCookieHttpOnly;
  @Override
  public Cookie cancelCookie(String name) {
    Cookie cookie = new Cookie(name, "");
    cookie.setMaxAge(0);
    return cookie;
  }

  @Override
  public String generateCookie(String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    String cookieHeader = "";


    LOGGER.debug(String.format("Cookie samesite: %s", this.cookieSameSite));
    LOGGER.debug(String.format("Cookie domain: %s", this.cookieDomain));
    LOGGER.debug(String.format("Cookie secure: %s", this.isCookieSecure));
    LOGGER.debug(String.format("Cookie path: %s", this.cookiePath));

    if(isCookieHttpOnly) {
      cookieHeader = isCookieSecure ?
              String.format("%s=%s; HttpOnly; Secure; Path=%s; SameSite=%S; Max-Age=%s", cookie.getName(), cookie.getValue(), cookiePath, cookieSameSite, maxAge) :
              String.format("%s=%s; HttpOnly; Path=%s; SameSite=%S; Max-Age=%s", cookie.getName(), cookie.getValue(), cookiePath, cookieSameSite, maxAge);
      return cookieHeader;
    }

    cookieHeader = isCookieSecure ?
            String.format("%s=%s; Secure; Path=%s; SameSite=%S; Max-Age=%s", cookie.getName(), cookie.getValue(), cookiePath, cookieSameSite, maxAge) :
            String.format("%s=%s; Path=%s; SameSite=%S; Max-Age=%s", cookie.getName(), cookie.getValue(), cookiePath, cookieSameSite, maxAge);

    return cookieHeader;
  }

  @Override
  public Cookie findCookie(HttpServletRequest request, String name) {

    Cookie[] cookies = request.getCookies();
    if(cookies == null || cookies.length == 0)
      return null;

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(name)) {
        return cookie;
      }
    }

    return null;
  }
}
