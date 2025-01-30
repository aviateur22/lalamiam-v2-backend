package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
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
  private final IApiLanguage apiLanguage;

  public ValidatorResourceMessageConfig(IApiLanguage apiLanguage) {
    this.apiLanguage = apiLanguage;
  }


  @Bean
  public MessageSource messageSource() {
    String languauage = apiLanguage.getValidatedLanguage();
    String bundleMessagePath = String.format(BUNDLE_ERROR_MESSAGE_PATH, languauage);
    DynamicMessageSource dynamicMessageSource = new DynamicMessageSource();
    dynamicMessageSource.updateMessageSource(bundleMessagePath);
    return dynamicMessageSource;
  }

  @Bean
  public LocalValidatorFactoryBean getValidator() {
    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }
}
