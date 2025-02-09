package com.ctoutweb.lalamiam.infra.annotation.custom.file;

import com.ctoutweb.lalamiam.infra.utility.FileUtility;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FilePdfIfNotEmptyValidator implements ConstraintValidator<FilePdfIfNotEmpty, MultipartFile> {
  private static final Logger LOGGER = LogManager.getLogger();
  private String message;

  @Override
  public void initialize(FilePdfIfNotEmpty constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
    this.message = constraintAnnotation.message();
  }

  @Override
  public boolean isValid(MultipartFile uploadFile, ConstraintValidatorContext context) {
    try {
      if(uploadFile.isEmpty() || uploadFile.getSize() == 0)
        return true;

      if(!uploadFile.isEmpty() && uploadFile.getSize() > 0 && FileUtility.isFilePdf(uploadFile.getInputStream()))
        return true;

      context.buildConstraintViolationWithTemplate(message)
              .addConstraintViolation()
              .disableDefaultConstraintViolation();
      return false;

    } catch (IOException exception) {
      LOGGER.error(()->String.format("[PdfFileValidator] - Exception: %s", exception));
      return false;
    }
  }
}
