package com.ctoutweb.lalamiam.infra.constant;

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
  public static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";  // Header key pour l'envoie d'un nouveau token CSRF
  public static final String FORM_CSRF_TOKEN = "FORM-CSRF-TOKEN";  // Header key pour la récupération d'un token CSRF lors de la soumission d'un formulaire
  public static final String COOKIE_CSRF_PARAM_NAME = "_csrf"; // Nom du cookie contenant le token CSRF
  public static final String HEADER_CSRF_PARAM_NAME = "_header";  // Token provenant du header
  public static final String COOKIE_CSRF_GENERATE_PARAM_NAME = "_csrf_generation_auth"; // Nom du cookie contenant le JWT permettant de générer un nouveau CSRF
  public static final String SEPARATOR = "+!!+";

}
