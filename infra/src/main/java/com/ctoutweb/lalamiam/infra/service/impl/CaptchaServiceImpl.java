package com.ctoutweb.lalamiam.infra.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory.ICaptchaStrategyFactory;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.service.ICaptchaService;
import com.ctoutweb.lalamiam.infra.service.ICookieService;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IJwtService;
import com.ctoutweb.lalamiam.infra.utility.IntegerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.CAPTCHA_ACCESS_KEY_PARAM_NAME;

@Service
public class CaptchaServiceImpl implements ICaptchaService {
  private final ICaptchaStrategyFactory captchaFactory;
  private final ICookieService cookieService;
  private final IJwtService jwtService;
  private final ICryptoService cryptoService;

  @Value("${captcha.access.token}")
  String captchaAccessToken;

  public CaptchaServiceImpl(
          ICaptchaStrategyFactory captchaFactory,
          ICookieService cookieService,
          IJwtService jwtService,
          ICryptoService cryptoService
  ) {
    this.captchaFactory = captchaFactory;
    this.cookieService = cookieService;
    this.jwtService = jwtService;
    this.cryptoService = cryptoService;
  }

  @Override
  public ICaptcha generateRandomCapatcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

    var cookieAccessCaptchaKeyValue = getCookieCaptchaAccessKey(request);

    if(cookieAccessCaptchaKeyValue == null || !isCaptchaAccessKeyValid(cookieAccessCaptchaKeyValue)) {
      manageUnvalidCaptchaAccessKey();
      return null;
    }

    CaptchaType randomCaptchaType = getRandomCaptcha();

    return  captchaFactory
            .getCaptchaStrategy(randomCaptchaType)
            .generateCaptcha();
  }

  @Override
  public HttpHeaders generateCaptchaAccessKey() {
    HttpHeaders headers = new HttpHeaders();
    IJwtIssue jwt = jwtService.generate(cryptoService.hashText(captchaAccessToken));
    headers.add(HttpHeaders.SET_COOKIE, cookieService.generateCookie(CAPTCHA_ACCESS_KEY_PARAM_NAME, jwt.getJwtToken()));
    return headers;
  }

  @Override
  public HttpHeaders generateCaptchaAccessKey(HttpHeaders headers) {
    IJwtIssue jwt = jwtService.generate(cryptoService.hashText(captchaAccessToken));
    headers.add(HttpHeaders.SET_COOKIE, cookieService.generateCookie(CAPTCHA_ACCESS_KEY_PARAM_NAME, jwt.getJwtToken()));
    return headers;
  }

  /**
   * Récupération d'un type de captcha en Random
   * @return CaptchaType
   */
  public CaptchaType getRandomCaptcha() {
    int randomSelection = IntegerUtil.generateNumberBetween(0, CaptchaType.values().length - 1);
    return CaptchaType.values()[randomSelection];
  }

  /**
   * Récupération du contenu du cookie possédant la clé
   * @param request HttpServletRequest
   * @return String - Contenu du cookie
   */
  public String getCookieCaptchaAccessKey(HttpServletRequest request) {
    var findcookie = cookieService.findCookie(request, CAPTCHA_ACCESS_KEY_PARAM_NAME);

    if(findcookie == null) {
      manageUnvalidCaptchaAccessKey();
      return null;
    }

    return findcookie.getValue();
  }

  /**
   * Vérification validité de la clé
   * @param jwtWithCaptchaAccessToken String  - Jwt contenant la clé
   * @return boolean
   */
  public boolean isCaptchaAccessKeyValid(String jwtWithCaptchaAccessToken) {
    DecodedJWT decodedJWT = jwtService.validateAndDecode(jwtWithCaptchaAccessToken);

    if(decodedJWT == null) {
      manageUnvalidCaptchaAccessKey();
      return false;
    }

    var hashCaptchaAccessToken = decodedJWT.getClaim("token").asString();

    if(hashCaptchaAccessToken == null) {
      manageUnvalidCaptchaAccessKey();
      return false;
    }

    return cryptoService.isHashValid(captchaAccessToken, hashCaptchaAccessToken);
  }

  /**
   * Suppression Cookie
   */
  public void manageUnvalidCaptchaAccessKey() {
    cookieService.cancelCookie(CAPTCHA_ACCESS_KEY_PARAM_NAME);
  }
}
