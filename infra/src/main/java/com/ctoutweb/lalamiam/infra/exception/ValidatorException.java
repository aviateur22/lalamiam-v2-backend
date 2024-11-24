package com.ctoutweb.lalamiam.infra.exception;

/**
 * Exception de validation des données en entrées API
 */
public class ValidatorException extends RuntimeException {
  public ValidatorException(String message) {
    super(message);
  }
}
