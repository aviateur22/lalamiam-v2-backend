package com.ctoutweb.lalamiam.infra.annotation.custom.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileNotemptyValidator.class)
@Documented
public @interface FileNotEmpty {
  String message() default "le fichier n'est peux pas être vide";
  Class<?>[] groups() default {};
  Class <? extends Payload>[] payload() default {};
}
