package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.infra.security.authentication.CustomAuthProvider;
import com.ctoutweb.lalamiam.infra.security.authentication.JwtFilter;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipalDetailService;
import com.ctoutweb.lalamiam.infra.security.csrf.CustomCsrfFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
  private final CustomCsrfFilter customCsrfFilter;
  private final CorsConfigurationSource corsConfigurationSource;
  private final CustomAuthProvider customAuthProvider;
  private final UserPrincipalDetailService userPrincipalDetailService;
  private final AccessDeniedHandler accessDeniedHandler;
  private final AuthenticationEntryPoint authenticationEntryPoint;

  private final JwtFilter jwtFilter;
  public WebSecurityConfig(
          CustomCsrfFilter customCsrfFilter,
          @Qualifier("corsConfiguration") CorsConfigurationSource corsConfigurationSource,
          CustomAuthProvider customAuthProvider,
          UserPrincipalDetailService userPrincipalDetailService,
          AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint, JwtFilter jwtFilter) {
    this.customCsrfFilter = customCsrfFilter;
    this.corsConfigurationSource = corsConfigurationSource;
    this.customAuthProvider = customAuthProvider;
    this.userPrincipalDetailService = userPrincipalDetailService;
    this.accessDeniedHandler = accessDeniedHandler;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.jwtFilter = jwtFilter;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .cors(cors->cors.configurationSource(corsConfigurationSource))
            .csrf(csrf->csrf.disable().addFilterBefore(customCsrfFilter, CsrfFilter.class))
            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception->exception
                            .accessDeniedHandler(accessDeniedHandler)
                            .authenticationEntryPoint(authenticationEntryPoint))
            .authorizeHttpRequests((authorizeHttpRequests) ->
              authorizeHttpRequests
                      .requestMatchers("/client/**").hasRole("CLIENT")
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            ).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


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
