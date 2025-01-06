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

  /**
   * Suppression d'un JWT a partir d'un email
   * @param email
   */
  public void deleteJwtByUserEmail(String email);

  /**
   * Sauvegarde d'un JWT lors de l'authentitcation
   * @param userId Long - Id de la personne qui se connecte
   * @param jwt IJwtIssue _ JWT issue
   * @param email String - Email de la personne
   */
  public void saveJwt(Long userId, IJwtIssue jwt, String email);

}
