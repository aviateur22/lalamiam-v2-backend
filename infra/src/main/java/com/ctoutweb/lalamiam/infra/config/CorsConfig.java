package com.ctoutweb.lalamiam.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.POST_CSRF_TOKEN;

@Configuration
public class CorsConfig {

  @Bean(name = "corsConfiguration")
  CorsConfigurationSource corsConfiguration() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    // Récupération des données d'initialisation de la webapp
    CorsConfiguration webAppInitialize = new CorsConfiguration();
    webAppInitialize.setAllowCredentials(true);
    webAppInitialize.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    webAppInitialize.setAllowedMethods(Arrays.asList("GET"));
    webAppInitialize.setAllowedHeaders(Arrays.asList("Content-Type"));

    source.registerCorsConfiguration("/auth/app-param", webAppInitialize);

    CorsConfiguration auth = new CorsConfiguration();
    auth.setAllowCredentials(true);
    auth.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    auth.setAllowedMethods(Arrays.asList("GET", "POST"));
    auth.setAllowedHeaders(Arrays.asList("Content-Type", POST_CSRF_TOKEN));
    auth.setExposedHeaders(Arrays.asList(POST_CSRF_TOKEN));
    source.registerCorsConfiguration("/auth/**", auth);

    CorsConfiguration admin = new CorsConfiguration();
    admin.setAllowCredentials(true);
    admin.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    admin.setAllowedMethods(Arrays.asList("GET", "POST"));
    admin.setAllowedHeaders(Arrays.asList("Content-Type", POST_CSRF_TOKEN));
    admin.setExposedHeaders(Arrays.asList(POST_CSRF_TOKEN));
    source.registerCorsConfiguration("/admin/**", admin);

    return  source;
  }
}
