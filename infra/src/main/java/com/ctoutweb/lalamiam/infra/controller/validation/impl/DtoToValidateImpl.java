package com.ctoutweb.lalamiam.infra.controller.validation.impl;

import com.ctoutweb.lalamiam.infra.annotation.AnnotationValidator;
import com.ctoutweb.lalamiam.infra.controller.validation.IDtoToValidate;
import jakarta.validation.Validator;

import java.util.List;

/**
 * Fabrication d'une liste de Dto pour vérification
 * @param dto1 Dto de type T a vérifier
 * @param <T>
 */
public record DtoToValidateImpl<T>(T dto1) implements IDtoToValidate {

  @Override
  public List<T> getDtosToValidate() {
    return List.of(dto1);
  }

  @Override
  public void validateDto(Validator validator) {
    AnnotationValidator<T> registerValidator = new AnnotationValidator<>(validator);

    // Récupération des DTO  la liste de a valider
    List<T> getDataToValidate = this.getDtosToValidate();

    if(!getDataToValidate.isEmpty())
      getDataToValidate.stream().forEach(inputData->registerValidator.validate(inputData));
  }
}
