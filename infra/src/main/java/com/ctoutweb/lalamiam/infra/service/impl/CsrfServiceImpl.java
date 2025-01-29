package com.ctoutweb.lalamiam.infra.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.security.csrf.ICustomCsrfTokenRepository;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.service.ICookieService;
import com.ctoutweb.lalamiam.infra.service.ICsrfService;
import com.ctoutweb.lalamiam.infra.service.IJwtService;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.*;

@Service
@PropertySource({"classpath:application.properties"})
public class CsrfServiceImpl implements ICsrfService {
  @Value("${csrf.access.token}")
  String csrfAccessToken;
  private final ICookieService cookieService;
  private final IJwtService jwtService;
  private final ICustomCsrfTokenRepository customCsrfTokenRepository;
  private final ICryptoService cryptoService;

  public CsrfServiceImpl(
          ICookieService cookieService,
          IJwtService jwtService,
          ICustomCsrfTokenRepository customCsrfTokenRepository,
          ICryptoService textHashService
  ) {
    this.cookieService = cookieService;
    this.jwtService = jwtService;
    this.customCsrfTokenRepository = customCsrfTokenRepository;
    this.cryptoService = textHashService;
  }
  @Override
  public void generateCsrf(HttpServletRequest request, HttpServletResponse response) {
    var cookieAccessCsrfKeyValue = getCookieCsrfAccessKey(request);

    if(cookieAccessCsrfKeyValue == null || !isCsrfAccessKeyValid(cookieAccessCsrfKeyValue)) {
      manageUnvalidCsrfAccessKey();
      return;
    }

    CsrfToken token = customCsrfTokenRepository.generateToken(request);
    customCsrfTokenRepository.saveToken(token, request, response);
  }

  @Override
  public HttpHeaders generateCsrfAccessKey() {
    HttpHeaders headers = new HttpHeaders();
    IJwtIssue jwt = jwtService.generate(cryptoService.hashText(csrfAccessToken));
    headers.add(HttpHeaders.SET_COOKIE, cookieService.generateCookie(
            COOKIE_CSRF_ACCESS_KEY_PARAM_NAME, jwt.getJwtToken(),
            ACCESS_CSRF_COOKIE_MAX_AGE
            ));
    return headers;
  }

  @Override
  public HttpHeaders clearCsrfAccessKey() {
    HttpHeaders headers = new HttpHeaders();
    IJwtIssue jwt = jwtService.generate(cryptoService.hashText(csrfAccessToken));
    headers.add(HttpHeaders.SET_COOKIE, cookieService.generateCookie(
            COOKIE_CSRF_ACCESS_KEY_PARAM_NAME, jwt.getJwtToken(),
            CSRF_COOKIE_MIN_AGE
    ));
    return headers;
  }

  /**
   * Récupération du contenu du cookie possédant la clé
   * @param request HttpServletRequest
   * @return String - Contenu du cookie
   */
  public String getCookieCsrfAccessKey(HttpServletRequest request) {
    var findcookie = cookieService.findCookie(request, COOKIE_CSRF_ACCESS_KEY_PARAM_NAME);

    if(findcookie == null) {
      manageUnvalidCsrfAccessKey();
      return null;
    }

    return findcookie.getValue();
  }

  /**
   * Vérification validité de la clé
   * @param jwtWithCsrfAccessToken String  - Jwt contenant la clé
   * @return boolean
   */
  public boolean isCsrfAccessKeyValid(String jwtWithCsrfAccessToken) {
    DecodedJWT decodedJWT = jwtService.validateAndDecode(jwtWithCsrfAccessToken);

    if(decodedJWT == null) {
      manageUnvalidCsrfAccessKey();
      return false;
    }

    var hashCsrfAccessToken = decodedJWT.getClaim("token").asString();

    if(hashCsrfAccessToken == null) {
      manageUnvalidCsrfAccessKey();
      return false;
    }

    return cryptoService.isHashValid(csrfAccessToken, hashCsrfAccessToken);
  }

  /**
   * Suppression Cookie
   */
  public void manageUnvalidCsrfAccessKey() {
    cookieService.cancelCookie(COOKIE_CSRF_ACCESS_KEY_PARAM_NAME);
  }

}
