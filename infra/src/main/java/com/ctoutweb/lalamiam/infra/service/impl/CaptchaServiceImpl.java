package com.ctoutweb.lalamiam.infra.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.captcha.CaptchaType;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;
import com.ctoutweb.lalamiam.infra.model.captcha.strategy.factory.ICaptchaStrategyFactory;
import com.ctoutweb.lalamiam.infra.model.security.ICaptchaToken;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.service.*;
import com.ctoutweb.lalamiam.infra.utility.NumberUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.ACCESS_CAPTCHA_COOKIE_MAX_AGE;
import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.CAPTCHA_ACCESS_KEY_PARAM_NAME;

@Service
public class CaptchaServiceImpl implements ICaptchaService {
  private static final Logger LOGGER = LogManager.getLogger();
  private final Factory factory;
  private final ICaptchaStrategyFactory captchaFactory;
  private final ICookieService cookieService;
  private final ITokenRepository tokenRepository;
  private final IJwtService jwtService;
  private final ICryptoService cryptoService;
  private final IMessageService messageService;
  @Value("${captcha.access.token}")
  String captchaAccessToken;
  @Value("${captcha.iv.key}")
  String captchaIvKey;

  public CaptchaServiceImpl(
          Factory factory, ICaptchaStrategyFactory captchaFactory,
          ICookieService cookieService,
          ITokenRepository tokenRepository, IJwtService jwtService,
          ICryptoService cryptoService,
          IMessageService messageService) {
    this.factory = factory;
    this.captchaFactory = captchaFactory;
    this.cookieService = cookieService;
    this.tokenRepository = tokenRepository;
    this.jwtService = jwtService;
    this.cryptoService = cryptoService;
    this.messageService = messageService;
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
  public Boolean isCaptchaReponseValid(IUserCaptchaResponse userCaptchaResponse) {

    // Récupération de l'ID au format LONG du captchaToken
    Long captchaTokenId = getCaptchaTokenId(userCaptchaResponse.getCaptchaResponseIdEncrypt());

    // CaptchaId non valide
    if(captchaTokenId == null) {
      LOGGER.error(()->"Le dechiffrement du captchaTokenId  n'est pas un Long valide");
      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));
    }

    TokenEntity captchaResponseEntity = tokenRepository.findById(captchaTokenId).orElse(null);

    // Captcha non tropuvé en base
    if(captchaResponseEntity == null) {
      LOGGER.error(()->"La réponse captcha n'existe pas en base de données");
      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));
    }

    ICaptchaToken captchaToken = factory.getImpl(captchaResponseEntity, userCaptchaResponse.getCaptchaResponseByUser(), cryptoService);

    return captchaToken
            .getCryptographicType()
            .isCaptchaResponseValid();
  }

  /**
   *
   * @param captchaResponseId
   * @return
   */
  public Long getCaptchaTokenId(String captchaResponseId) {
    byte[] captchaIvKeyByte = cryptoService.getByteArrayFromBase64(captchaIvKey);

    if(captchaIvKeyByte == null){
      LOGGER.error(()->String.format("Impossible de produire un byte[] à partir de %s", captchaIvKey));
      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));
    }

    String decryptCaptchaResponseId = cryptoService.decrypt(captchaResponseId, captchaIvKeyByte);

    if(NumberUtil.isStringValidLong(decryptCaptchaResponseId))
      return Long.parseLong(decryptCaptchaResponseId);

    // Le dechiffrement n'est pas un LONG
    return null;
  }

  @Override
  public HttpHeaders generateCaptchaAccessKey() {
    HttpHeaders headers = new HttpHeaders();
    IJwtIssue jwt = jwtService.generate(cryptoService.hashText(captchaAccessToken));
    headers.add(HttpHeaders.SET_COOKIE, cookieService.generateCookie(
            CAPTCHA_ACCESS_KEY_PARAM_NAME,
            jwt.getJwtToken(),
            ACCESS_CAPTCHA_COOKIE_MAX_AGE
    ));
    return headers;
  }

  @Override
  public HttpHeaders generateCaptchaAccessKey(HttpHeaders headers) {
    IJwtIssue jwt = jwtService.generate(cryptoService.hashText(captchaAccessToken));
    headers.add(HttpHeaders.SET_COOKIE, cookieService.generateCookie(
            CAPTCHA_ACCESS_KEY_PARAM_NAME,
            jwt.getJwtToken(),
            ACCESS_CAPTCHA_COOKIE_MAX_AGE
    ));
    return headers;
  }

  /**
   * Récupération d'un type de captcha en Random
   * @return CaptchaType
   */
  public CaptchaType getRandomCaptcha() {
    int randomSelection = NumberUtil.generateNumberBetween(0, CaptchaType.values().length - 1);
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
