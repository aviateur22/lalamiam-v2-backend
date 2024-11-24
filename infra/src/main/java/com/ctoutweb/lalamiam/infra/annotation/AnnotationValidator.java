package com.ctoutweb.lalamiam.infra.annotation;

import com.ctoutweb.lalamiam.infra.exception.ValidatorException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AnnotationValidator <T> {

  private final Validator validator;

  public AnnotationValidator(Validator validator) {
    this.validator = validator;
  }

  /**
   * Validation des données en entrée de l'API
   *
   * En cas d'erreur, les fichiers validatorErrorMessage.properties seront utilisés
   * pour renvoyer un message d'erreur.
   *
   * Les fichiers sont initialisé grace:
   * @see com.ctoutweb.lalamiam.infra.config.ValidatorResourceMessageConfig
   *
   * Le message d'erreur est récupéré par la méthode:
   * ConstraintViolation::getMessage
   *
   * @param objectToValidate T - Données client à valider
   */
  public void validate(T objectToValidate)  {
    Set<ConstraintViolation<T>> validationErrors = validator.validate(objectToValidate);

    // Récuperation derniere erreur
    String lastError = null;

    if(!validationErrors.isEmpty()) {
      lastError = validationErrors.stream().reduce((first, second)->second).map(ConstraintViolation::getMessage).orElse(null);

      throw new ValidatorException(lastError);
    }
  }

}
