package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.security.csrf.CustomCsrfFilter;
import com.ctoutweb.lalamiam.infra.security.csrf.CustomCsrfTokenRepositoryImpl;
import com.ctoutweb.lalamiam.infra.security.csrf.ICustomCsrfTokenRepository;
import com.ctoutweb.lalamiam.infra.service.ICookieService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
  @Bean
  public ICustomCsrfTokenRepository customCsrfTokenRepository(ICookieService cookieService) {
    return new CustomCsrfTokenRepositoryImpl(cookieService);
  }

  @Bean
  public CustomCsrfFilter csrfFilter(ICustomCsrfTokenRepository customCsrfTokenRepository) {
    return new CustomCsrfFilter(customCsrfTokenRepository);
  }
}
