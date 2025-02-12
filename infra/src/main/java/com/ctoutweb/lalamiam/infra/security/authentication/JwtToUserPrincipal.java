package com.ctoutweb.lalamiam.infra.security.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToUserPrincipal {
  private static final Logger LOGGER = LogManager.getLogger();
  private final Factory factory;
  public JwtToUserPrincipal(Factory factory) {
    this.factory = factory;
  }

  public UserPrincipal convertJwtToUserPrincipal(DecodedJWT decodeJwt) {
    final boolean IS_ACCOUNT_ACTIVE = true;
    LOGGER.debug(()->String.format("[JwtToUserPrincipal] - Convertion Jwt to UserPrincipal: %s", decodeJwt));
    List<SimpleGrantedAuthority> authorities = getGrantedAuthoritiesFromClaim(decodeJwt);
    return factory.getUserPrincipal(Long.valueOf(
            decodeJwt.getClaim("userId").asLong()),
            decodeJwt.getSubject(),
            "",
            IS_ACCOUNT_ACTIVE,
            authorities);
  }

  private List<SimpleGrantedAuthority> getGrantedAuthoritiesFromClaim(DecodedJWT decodeJwt) {
    LOGGER.debug(()->String.format("[JwtToUserPrincipal] - Convertion JWT claims en SimpleGrantedAuthority: %s", decodeJwt.getClaim("authorities")));
    var claim = decodeJwt.getClaim("authorities");
    if(claim.isNull() || claim.isMissing()) {
      LOGGER.info(()->"[JwtToUserPrincipal] - Claim vide - pas de SimpleGrantedAuthority");
      return List.of();
    }
    return claim.asList(SimpleGrantedAuthority.class);
  }

}
