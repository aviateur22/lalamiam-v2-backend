package com.ctoutweb.lalamiam.infra.annotation.custom.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilePdfIfNotEmptyValidator.class)
@Documented
public @interface FilePdfIfNotEmpty {
  String message() default "Le fichier doit être un PDF";
  Class<?>[] groups() default {};
  Class <? extends Payload>[] payload() default {};
}
