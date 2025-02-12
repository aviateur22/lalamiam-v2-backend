package com.ctoutweb.lalamiam.infra.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.repository.IJwtRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.JwtEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.service.IJwtService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@PropertySource({"classpath:application.properties"})
public class JwtServiceImpl implements IJwtService {
  private static final Logger LOGGER = LogManager.getLogger();
  private final Factory factory;
  private final IJwtRepository jwtRepository;
  @Value("${jwt.validity.hour}")
  Long jwtValidity;
  @Value("${jwt.secret.key}")
  String jwtSecret;
  @Value("${jwt.issuer}")
  String jwtIssuer;
  @Value("${zone.id}")
  String zoneId;

  public JwtServiceImpl(Factory factory, IJwtRepository jwtRepository) {
    this.factory = factory;
    this.jwtRepository = jwtRepository;
  }

  @Override
  public IJwtIssue generate(UserPrincipal user) {

    Instant expiredAt = Instant.now().plus(Duration.ofHours(jwtValidity));
    byte[] timeNow = ("time now" +" " + System.currentTimeMillis()).getBytes();
    String jwtId = UUID.nameUUIDFromBytes(timeNow).toString();

    List<String> authorities = user.getAuthorities()
            .stream()
            .map(auth->auth.toString())
            .collect(Collectors.toList());

    String token = JWT.create()
            .withSubject(user.getUsername())
            .withJWTId(jwtId)
            .withIssuer(jwtIssuer)
            .withExpiresAt(expiredAt)
            .withClaim("userId", user.getId())
            .withClaim("authorities", authorities)
            .sign(Algorithm.HMAC256(jwtSecret));

    return factory.getImpl(jwtId, token, expiredAt.atZone(ZoneId.of(zoneId)));
  }

  @Override
  public IJwtIssue generate(String cryptoToken) {
    Instant expiredAt = Instant.now().plus(Duration.ofHours(jwtValidity));
    byte[] timeNow = ("time now" +" " + System.currentTimeMillis()).getBytes();
    String jwtId = UUID.nameUUIDFromBytes(timeNow).toString();

    String token = JWT.create()
            .withJWTId(jwtId)
            .withIssuer(jwtIssuer)
            .withExpiresAt(expiredAt)
            .withClaim("token", cryptoToken)
            .sign(Algorithm.HMAC256(jwtSecret));

    return factory.getImpl(jwtId, token, expiredAt.atZone(ZoneId.of(zoneId)));
  }

  @Override
  public DecodedJWT validateAndDecode(String token) {
    LOGGER.debug(()->String.format("[JwtServiceImpl] - validateAndDecode jwt token: %s", token));
    return JWT.require(Algorithm.HMAC256(jwtSecret))
            .build()
            .verify(token);
  }

  @Override
  public void deleteJwtByUserEmail(String email) {
    if(jwtRepository.findOneByEmail(email).isPresent()){
      jwtRepository.deleteByEmail(email);
    }
  }

  @Override
  public void saveJwt(Long userId, IJwtIssue jwt, String email) {
    UserEntity userLogin = new UserEntity();
    userLogin.setId(userId);

    JwtEntity insertJwtLogin = new JwtEntity();
    insertJwtLogin.setJwtToken(jwt.getJwtToken());
    insertJwtLogin.setJwtId(jwt.getJwtId());
    insertJwtLogin.setUser(userLogin);
    insertJwtLogin.setExpiredAt(jwt.getExpiredAt());
    insertJwtLogin.setEmail(email);
    insertJwtLogin.setIsValid(true);
    jwtRepository.save(insertJwtLogin);
  }
}
