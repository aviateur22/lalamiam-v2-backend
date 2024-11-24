package com.ctoutweb.lalamiam.infra.annotation.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Documented
public @interface PasswordConstraint {
  public  String message() default "Le format du mot de passe n'est pas valide";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
