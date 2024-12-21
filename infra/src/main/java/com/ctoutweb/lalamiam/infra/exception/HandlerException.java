package com.ctoutweb.lalamiam.infra.exception;

import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
  private final Factory factory;

  public HandlerException(Factory factory) {
    this.factory = factory;
  }

  @ExceptionHandler(value = {com.ctoutweb.lalamiam.infra.exception.BadRequestException.class})
  public ResponseEntity<IErrorMessage> badRequestException(com.ctoutweb.lalamiam.infra.exception.BadRequestException badRequestException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(badRequestException.getMessage()), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(value = {ValidatorException.class})
  public ResponseEntity<IErrorMessage> validatorException(ValidatorException validatorException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(validatorException.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {BadRequestException.class})
  public ResponseEntity<IErrorMessage> badRequestException(BadRequestException badRequestException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(badRequestException.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {ConflictException.class})
  public ResponseEntity<IErrorMessage> conflictException(ConflictException conflictException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(conflictException.getMessage()), HttpStatus.CONFLICT);
  }
}
