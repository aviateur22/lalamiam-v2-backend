package com.ctoutweb.lalamiam.infra.constant;

public class ApplicationConstant {
  private ApplicationConstant() {
    throw new IllegalStateException("Class static");
  }

  public static final String WORD_TO_REPLACE = "!%%!";
  public static final String EXCEPTION_MESSAGE_FILE = String.format("message/exceptionMessage_%S.properties", WORD_TO_REPLACE);
  public static final String EMAIL_MESSAGE_FILE = String.format("message/emailSubjectMessage_%S.properties", WORD_TO_REPLACE);
  public static final String SUCCESS_MESSAGE_FILE = String.format("message/successMessage_%S.properties", WORD_TO_REPLACE);
  public static final String VALIDATOR_MESSAGE_FILE = String.format("message/validatorErrorMessage_%S.properties", WORD_TO_REPLACE);
  public static final String CAPTCHA_MESSAGE_FILE = String.format("message/captchaMessage_%S.properties", WORD_TO_REPLACE);
}
