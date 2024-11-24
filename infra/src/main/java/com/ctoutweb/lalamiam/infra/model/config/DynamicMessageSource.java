package com.ctoutweb.lalamiam.infra.model.config;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.BUNDLE_CLASSPATH;
import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.DEFAULT_ENCODING;

/**
 * Implementation Custom MessageSource pouvant avoir
 * la source du fichier modifié si besoin
 */
public class DynamicMessageSource extends ReloadableResourceBundleMessageSource {

  /**
   * Mise a jour du MessageSource
   * @param bundleMessagePath String - Path du bundle a charger
   */
  public void updateMessageSource(String bundleMessagePath) {
    clearCache();
    setBasenames(BUNDLE_CLASSPATH + bundleMessagePath);
    setDefaultEncoding(DEFAULT_ENCODING);
  }
}
