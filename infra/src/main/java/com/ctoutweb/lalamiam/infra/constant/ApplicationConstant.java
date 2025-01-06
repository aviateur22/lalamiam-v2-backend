package com.ctoutweb.lalamiam.infra.constant;

import com.ctoutweb.lalamiam.infra.model.image.ImageType;

public class ApplicationConstant {
  private ApplicationConstant() {
    throw new IllegalStateException("Class static");
  }

  // API message
  public static final String WORD_TO_REPLACE = "!%%!";
  public static final String EXCEPTION_MESSAGE_FILE = String.format("message/exceptionMessage_%S.properties", WORD_TO_REPLACE);
  public static final String EMAIL_MESSAGE_FILE = String.format("message/emailSubjectMessage_%S.properties", WORD_TO_REPLACE);
  public static final String SUCCESS_MESSAGE_FILE = String.format("message/successMessage_%S.properties", WORD_TO_REPLACE);
  public static final String VALIDATOR_MESSAGE_FILE = String.format("message/validatorErrorMessage_%S.properties", WORD_TO_REPLACE);
  public static final String CAPTCHA_MESSAGE_FILE = String.format("message/captchaMessage_%S.properties", WORD_TO_REPLACE);

  // MessageSource pour  @Javax.validation.constraint.message
  public static final String DEFAULT_ENCODING = "UTF-8";
  public static final String BUNDLE_CLASSPATH ="classpath:";
  public static final String BUNDLE_ERROR_MESSAGE_PATH = "message/validatorErrorMessage";

  // CSRF Token
  public static final String POST_CSRF_TOKEN = "POST-CSRF-TOKEN";  // Header key pour l'envoie et récupération token CSRF
  public static final String COOKIE_CSRF_PARAM_NAME = "_csrf"; // Nom du cookie contenant le token CSRF
  public static final String HEADER_CSRF_PARAM_NAME = "_header";  // Token provenant du header
  public static final String COOKIE_CSRF_ACCESS_KEY_PARAM_NAME = "_csrf_access_key"; // Nom du cookie contenant le JWT permettant de générer un nouveau CSRF
  public static final String SEPARATOR = "+!!+";

  // CAPTCHA
  public static final String CAPTCHA_ACCESS_KEY_PARAM_NAME = "_captcha_access_key"; // Nom du cookie contenant le token JWT permettant de générer un nouveau Captcha

  // Captcha Image data
  public static final int IMAGE_WIDTH = 300;
  public static final int IMAGE_HEIGHT = 100;
  public static final ImageType APP_IMAGE_TYPE = ImageType.PNG;

  // Crypto
  public static final int CRYPTO_ITERATION_COUNT = 65536;
  public static final int CRYPTO_KEY_LENGTH = 256;

  // LOGIN

  public static final long LOGIN_ERROR_ATTEMPT_AVAILABLE = 4;

}
