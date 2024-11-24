package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.model.config.DynamicMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.MessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.BUNDLE_ERROR_MESSAGE_PATH;

/**
 * Gestion des messages de @Javax.validation.constraint.message
 * Cette classe permets de charger les messages de validation à partir d'un fichier properties.
 *
 * ex:
 * @NotNull(message = "{email.missing}")
 * @NotBlank(message = "{email.missing}")
 * @Email(message = "{email.bad.format}")
 * String email,
 */
@Configuration
public class ValidatorResourceMessageConfig {

  @Bean
  public MessageSource messageSource() {
    DynamicMessageSource dynamicMessageSource = new DynamicMessageSource();
    dynamicMessageSource.updateMessageSource(BUNDLE_ERROR_MESSAGE_PATH);
    return dynamicMessageSource;
  }

  @Bean
  public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }
}
