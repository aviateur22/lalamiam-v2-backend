//package com.ctoutweb.lalamiam.infra.controller;
//
//import com.ctoutweb.lalamiam.infra.annotation.AnnotationValidator;
//import com.ctoutweb.lalamiam.infra.factory.Factory;
//import com.ctoutweb.lalamiam.infra.controller.validation.IDtoToValidate;
//import jakarta.validation.Validator;
//
//import java.util.List;
//
///**
// * Validation des données en entrée API
// */
//public class BaseControllerImpl implements IBaseController {
//  private final Validator validator;
//
//  private final Factory factory;
//
//  public BaseControllerImpl(Validator validator, Factory factory) {
//    this.validator = validator;
//    this.factory = factory;
//  }
//
//  @Override
//  public <T> void validateDto(IDtoToValidate dtosToValidate) {
//    AnnotationValidator<T> registerValidator = new AnnotationValidator<>(this.validator);
//
//    // Récupération des DTO  la liste de a valider
//    List<T> getDataToValidate = dtosToValidate.getDtosToValidate();
//
//    if(!getDataToValidate.isEmpty())
//      getDataToValidate.stream().forEach(inputData->registerValidator.validate(inputData));
//  }
//}
