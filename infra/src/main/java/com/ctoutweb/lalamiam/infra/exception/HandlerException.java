package com.ctoutweb.lalamiam.infra.exception;

import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
  @ExceptionHandler(value = {ValidatorException.class})
  public ResponseEntity<String> validatorException(ValidatorException validatorException) {
    return new ResponseEntity<>(validatorException.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {BadRequestException.class})
  public ResponseEntity<String> validatorException(BadRequestException badRequestException) {
    return new ResponseEntity<>(badRequestException.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
