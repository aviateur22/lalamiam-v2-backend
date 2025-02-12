package com.ctoutweb.lalamiam.infra.security.authentication.helper;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface IJwtFilterHelper {
  IJwtFilterHelper isJwtValid();
  IJwtFilterHelper extractJwtFromHeaders(HttpServletRequest request);
  IJwtFilterHelper decodeJwt();
  Optional<DecodedJWT> getDecodedJwt();
}
