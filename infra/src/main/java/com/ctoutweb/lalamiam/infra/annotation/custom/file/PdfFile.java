package com.ctoutweb.lalamiam.infra.annotation.custom.file;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PdfFileValidator.class)
@Documented
public @interface PdfFile {
  String message() default "le fichier n'est pas valide";
  Class<?>[] groups() default {};
  Class <? extends Payload>[] payload() default {};
}
