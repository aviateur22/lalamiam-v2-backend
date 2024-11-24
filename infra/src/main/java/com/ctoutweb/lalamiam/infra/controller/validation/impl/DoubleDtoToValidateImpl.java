package com.ctoutweb.lalamiam.infra.controller.validation.impl;

import com.ctoutweb.lalamiam.infra.annotation.AnnotationValidator;
import com.ctoutweb.lalamiam.infra.controller.validation.IDtoToValidate;
import jakarta.validation.Validator;

import java.util.List;

/**
 * Fabrication d'une liste de Dto pour vérification
 * @param dto1 - 1Er Dto de type T a vérifier
 * @param dto2 - 2nd Dto de type T à vérifier
 * @param <T>
 */
public record DoubleDtoToValidateImpl<T>(T dto1, T dto2) implements IDtoToValidate {

  @Override
  public List<T> getDtosToValidate() {
    return List.of(dto1, dto2);
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
