package com.ctoutweb.lalamiam.infra.security.authentication;

import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.security.authentication.helper.IJwtFilterHelper;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
  private static final Logger LOGGER = LogManager.getLogger();
  private final IMessageService messageService;
  private final IJwtFilterHelper jwtFilterHelper;
  private final JwtToUserPrincipal jwtToUserPrincipal;

  public JwtFilter(
          IMessageService messageService,
          IJwtFilterHelper jwtFilterHelper,
          JwtToUserPrincipal jwtToUserPrincipal) {
    this.messageService = messageService;
    this.jwtFilterHelper = jwtFilterHelper;
    this.jwtToUserPrincipal = jwtToUserPrincipal;
  }
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    LOGGER.debug(()->"[JwtFilter] - Extraction Bearer token des headers pour control");
    jwtFilterHelper
            .extractJwtFromHeaders(request)
            .decodeJwt()
            .isJwtValid()
            .getDecodedJwt()
            .map(jwtToUserPrincipal::convertJwtToUserPrincipal)
            .map(UserPrincipalAuthenticationToken::new)
            .ifPresentOrElse(
                    auth-> SecurityContextHolder.getContext().setAuthentication(auth),
                    ()->new AuthException(messageService.getMessage("jwt.unvalid"))
            );

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    // Exclude "/auth/**" from being filtered
    String path = request.getRequestURI();
    return path.contains("/auth/");
  }
}
