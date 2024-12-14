package com.ctoutweb.lalamiam.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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

    return  source;
  }
}
