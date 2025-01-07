package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.security.authentication.CustomAuthProvider;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipalDetailService;
import com.ctoutweb.lalamiam.infra.security.csrf.CustomCsrfFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  private final CustomCsrfFilter customCsrfFilter;
  private final CorsConfigurationSource corsConfigurationSource;
  private final CustomAuthProvider customAuthProvider;
  private final UserPrincipalDetailService userPrincipalDetailService;
  public WebSecurityConfig(
          CustomCsrfFilter customCsrfFilter,
          @Qualifier("corsConfiguration") CorsConfigurationSource corsConfigurationSource,
          CustomAuthProvider customAuthProvider,
          UserPrincipalDetailService userPrincipalDetailService
  ) {
    this.customCsrfFilter = customCsrfFilter;
    this.corsConfigurationSource = corsConfigurationSource;
    this.customAuthProvider = customAuthProvider;
    this.userPrincipalDetailService = userPrincipalDetailService;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(cors->cors.configurationSource(corsConfigurationSource))
            .csrf(csrf->csrf.disable().addFilterBefore(customCsrfFilter, CsrfFilter.class))
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception->exception.authenticationEntryPoint(
                    (request, response, authenticationException) -> {
                      response.setContentType("application/json");
                      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                      Map<String, String> errorResponse = new HashMap<>();
                      errorResponse.put("error", authenticationException.getMessage());
                      ObjectMapper mapper = new ObjectMapper();
                      response.getOutputStream().write(mapper.writeValueAsBytes(errorResponse));
                    }
            ))
            .authorizeHttpRequests(request->request
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            );

    return http.build();
  }

  @Bean
  AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(customAuthProvider)
        .userDetailsService(userPrincipalDetailService)
        .and().build();
  }
}
