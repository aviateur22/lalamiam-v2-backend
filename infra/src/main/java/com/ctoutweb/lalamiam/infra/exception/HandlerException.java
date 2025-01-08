package com.ctoutweb.lalamiam.infra.exception;

import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import com.ctoutweb.lalamiam.core.exception.ConflictException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IErrorMessage;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.postgresql.util.PSQLException;

@ControllerAdvice
public class HandlerException {
  private static final Logger LOGGER = LogManager.getLogger();
  private final Factory factory;
  private final IMessageService messageService;

  public HandlerException(Factory factory, IMessageService messageService) {
    this.factory = factory;
    this.messageService = messageService;
  }

  @ExceptionHandler(value = {com.ctoutweb.lalamiam.infra.exception.BadRequestException.class})
  public ResponseEntity<IErrorMessage> badRequestException(com.ctoutweb.lalamiam.infra.exception.BadRequestException badRequestException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(badRequestException.getMessage()), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(value = {ValidatorException.class})
  public ResponseEntity<IErrorMessage> validatorException(ValidatorException validatorException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(validatorException.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {PropertiesException.class})
  public ResponseEntity<IErrorMessage> captchaException(PropertiesException captchaException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(captchaException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }
  @ExceptionHandler(value = {BadRequestException.class})
  public ResponseEntity<IErrorMessage> badRequestException(BadRequestException badRequestException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(badRequestException.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {ConflictException.class})
  public ResponseEntity<IErrorMessage> conflictException(ConflictException conflictException) {
    return new ResponseEntity<>(factory.getErrorMessageImpl(conflictException.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = {PSQLException.class})
  public ResponseEntity<IErrorMessage> psqlException(PSQLException psqlException) {
    LOGGER.error(()->String.format("PSQL Erreur: %s", psqlException.getMessage()));
    return new ResponseEntity<>(factory.getErrorMessageImpl(messageService.getMessage("error")), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {AuthException.class})
  public ResponseEntity<IErrorMessage> authException(AuthException authException) {
    LOGGER.error(()->String.format("AuthException Erreur: %s", authException.getMessage()));
    return new ResponseEntity<>(factory.getErrorMessageImpl(authException.getMessage()), HttpStatus.BAD_REQUEST);
  }
}
