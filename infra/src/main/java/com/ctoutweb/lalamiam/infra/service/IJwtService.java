package com.ctoutweb.lalamiam.infra.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;

public interface IJwtService {

  /**
   * Génération d'un JWT incluant les données UserPrincipal
   * @param userPrincipal UserPrincipal - Personne authentifié générant un Jwt
   * @return IJwtIssue
   */
  public IJwtIssue generate(UserPrincipal userPrincipal);

  /**
   * Génération d'un JWT avec un token
   * @param cryptoToken String - Token utilisant la cryptographie qui sera intégré dans les claims du JWT
   * @return IJwtIssue
   */
  public IJwtIssue generate(String cryptoToken);

  /**
   * Decoded un JWT
   * @param token String - Jwt à verifier
   * @return DecodedJWT
   */
  public DecodedJWT validateAndDecode(String token);

}
