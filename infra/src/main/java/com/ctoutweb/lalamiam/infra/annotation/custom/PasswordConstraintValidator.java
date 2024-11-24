package com.ctoutweb.lalamiam.infra.annotation.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordConstraint, String> {
  private String message;
  private final int MIN_LENGTH = 11;
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    PasswordValidator passwordValidator = getPasswordRule();
    RuleResult result = passwordValidator.validate(new PasswordData(password));

    if(result.isValid()) return true;

    context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();

    return false;
  }

  /**
   *
   * @return PasswordValidator
   */
  private PasswordValidator getPasswordRule() {
    PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
            new LengthRule(MIN_LENGTH, 30),
            new CharacterRule(EnglishCharacterData.UpperCase, 1),
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit,1),
            new CharacterRule(EnglishCharacterData.Special, 1),
            new WhitespaceRule()
    ));
    return passwordValidator;
  }
}
