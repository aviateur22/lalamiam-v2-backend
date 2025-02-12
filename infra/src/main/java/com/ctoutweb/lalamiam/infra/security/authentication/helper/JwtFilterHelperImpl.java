package com.ctoutweb.lalamiam.infra.security.authentication.helper;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.factory.EntityFactory;
import com.ctoutweb.lalamiam.infra.repository.IJwtRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.JwtEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.IJwtService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.AUTHORIZATION_HEADER_NAME;
import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.BEARER_NAME;

@Component
public class JwtFilterHelperImpl implements IJwtFilterHelper {
  private final IJwtRepository jwtRepository;
  private final IJwtService jwtService;
  private final IMessageService messageService;
  private String tokenFromHeader = null; // Token extrait des headers
  private DecodedJWT decodedJWT = null; // Decoded JWT
  private Boolean isJwtValid = false; // Validation des données contenu dans le JWT
  private final IUserRepository userRepository;

  public JwtFilterHelperImpl(
          IJwtRepository jwtRepository,
          IJwtService jwtService,
          IMessageService messageService,
          IUserRepository userRepository) {
    this.jwtRepository = jwtRepository;
    this.jwtService = jwtService;
    this.messageService = messageService;
    this.userRepository = userRepository;
  }
  @Override
  public IJwtFilterHelper extractJwtFromHeaders(HttpServletRequest request) {
    // Réinitialisation des données
    reInitializeValues();

    var token = request.getHeader(AUTHORIZATION_HEADER_NAME);
    if(!StringUtils.hasText(token) || !token.startsWith(BEARER_NAME))
      return this;

    this.tokenFromHeader = token.substring(7);
    return this;
  }
  @Override
  public IJwtFilterHelper isJwtValid() {
    if(decodedJWT == null) {
      this.isJwtValid = false;
      return this;
    }

    Long userId = decodedJWT.getClaim("userId").asLong();
    String jwtToken = decodedJWT.getToken();
    String jwtId = decodedJWT.getId();
    UserEntity findUser = userRepository.findById(userId).orElse(null);
    JwtEntity findJwt = jwtRepository.findOneByUser(findUser).orElse(null);

    this.isJwtValid = findJwt != null && findJwt.getJwtId().equals(jwtId) && findJwt.getIsValid() == true && findJwt.getJwtToken().equals(jwtToken);
    return this;
  }

  @Override
  public IJwtFilterHelper decodeJwt() {
    try {
      // Si headerToken vide
      if(this.tokenFromHeader == null) {
        this.decodedJWT = null;
        return this;
      }

      this.decodedJWT = jwtService.validateAndDecode(tokenFromHeader);
    } catch (JWTVerificationException exception) {
      throw new AuthException(messageService.getMessage("jwt.unvalid"));
    }
    return this;
  }

  @Override
  public Optional<DecodedJWT> getDecodedJwt() {
    if(this.decodedJWT == null)
      return Optional.empty();

    return Optional.of(decodedJWT);
  }

  private void reInitializeValues() {
   tokenFromHeader = null; // Token extrait des headers
   decodedJWT = null; // Decoded JWT
   isJwtValid = false;
  }

  //////////////////


  public String getTokenFromHeader() {
    return tokenFromHeader;
  }

  public DecodedJWT getDecodedJWT() {
    return decodedJWT;
  }
  public Boolean getJwtValid() {
    return isJwtValid;
  }


}
