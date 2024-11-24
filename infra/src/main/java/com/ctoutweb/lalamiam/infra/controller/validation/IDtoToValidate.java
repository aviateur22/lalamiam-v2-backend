package com.ctoutweb.lalamiam.infra.controller.validation;

import jakarta.validation.Validator;

import java.util.List;

public interface IDtoToValidate {

  /**
   * Liste les differents object DTO a controller en entrée d'API
   * @param <T>
   * @return List<T>
   */
  public <T> List<T> getDtosToValidate();

  public <T> void validateDto(Validator validator);
}
