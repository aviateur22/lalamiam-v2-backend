package com.ctoutweb.lalamiam.infra.exception;

import com.ctoutweb.lalamiam.infra.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {
  @ExceptionHandler(value = {ValidatorException.class})
  public ResponseEntity<ErrorResponseDto> validatorException(ValidatorException validatorException) {
    ErrorResponseDto responseDto = new ErrorResponseDto(validatorException.getMessage());
    return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
  }
}
