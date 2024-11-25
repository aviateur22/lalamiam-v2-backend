package com.ctoutweb.lalamiam.infra.security.csrf;

import com.ctoutweb.lalamiam.infra.utility.HttpServletUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Pattern;

public class CustomCsrfFilter extends OncePerRequestFilter {
  private static final Logger LOGGER = LogManager.getLogger();
  private final ICustomCsrfTokenRepository customCsrfTokenRepository;

  public CustomCsrfFilter(ICustomCsrfTokenRepository customCsrfTokenRepository) {
    this.customCsrfTokenRepository = customCsrfTokenRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    CsrfToken csrfCookieToken = customCsrfTokenRepository.loadHeaderToken(request);
    CsrfToken headerCsrfFormToken = customCsrfTokenRepository.loadHeaderToken(request);

    RequestMatcher csrfFilterMatcher = new DefaultRequiresCsrfMatcher();

    if(!csrfFilterMatcher.matches(request)){
      filterChain.doFilter(request, response);
      return;
    }

    if(!areCsrfHeaderCookieEquals(headerCsrfFormToken, csrfCookieToken)) {

      if(csrfCookieToken != null && headerCsrfFormToken != null) {
        LOGGER.error(String.format("Données Token %s, %s",csrfCookieToken.getToken(), headerCsrfFormToken.getToken()));
        LOGGER.error(String.format("Erreur TOKEN CSRF - Path: %s  - CSRF Formulaire Header: %s - CSRF Cookie: %s",
                request.getRequestURI(),
                headerCsrfFormToken != null ? headerCsrfFormToken.toString() : null,
                csrfCookieToken != null ? csrfCookieToken.toString() : null)
        );
      }

      LOGGER.error("CSRF token ou COOKIE token == null");

      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      HttpServletUtility.formatResponseMessage( response,"error", "Le token CSRF n'est pas valide");
      return;
    }
    filterChain.doFilter(request, response);
  }

  public boolean areCsrfHeaderCookieEquals(CsrfToken headerCsrfFormToken, CsrfToken csrfCookieToken) {
    return headerCsrfFormToken != null && csrfCookieToken != null && csrfCookieToken.getToken().equals(headerCsrfFormToken.getToken());
  }
  public static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
    private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    @Override
    public boolean matches(HttpServletRequest request) {
      return !allowedMethods.matcher(request.getMethod()).matches();
    }
  }
}
