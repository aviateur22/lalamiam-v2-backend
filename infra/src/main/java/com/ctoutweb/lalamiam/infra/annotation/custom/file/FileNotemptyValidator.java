package com.ctoutweb.lalamiam.infra.annotation.custom.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileNotemptyValidator implements ConstraintValidator<FileNotEmpty, MultipartFile> {
  private String message;
  @Override
  public void initialize(FileNotEmpty constraintAnnotation) {
    this.message = constraintAnnotation.message();
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(MultipartFile uploadFile, ConstraintValidatorContext context) {
    if(!uploadFile.isEmpty() || uploadFile.getSize() > 0)
      return true;

    context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

    return false;
  }
}
