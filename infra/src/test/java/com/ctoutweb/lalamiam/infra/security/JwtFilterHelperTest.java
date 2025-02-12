package com.ctoutweb.lalamiam.infra.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.repository.IJwtRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.JwtEntity;
import com.ctoutweb.lalamiam.infra.security.authentication.helper.JwtFilterHelperImpl;
import com.ctoutweb.lalamiam.infra.service.IJwtService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.service.impl.JwtServiceImpl;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.AUTHORIZATION_HEADER_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtFilterHelperTest {
  @Mock
  IJwtRepository mockedJwtRepository;
  @Mock
  HttpServletRequest mockedHttpServletRequest;
  @Mock
  DecodedJWT mockedDecodedJWT;
  @Mock
  IJwtService mockJwtDecode;
  @Mock
  IUserRepository userRepository;
  @Mock
  IMessageService mockMessageService;
  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }
  @Test
  public void jwt_should_be_valid() {
    JwtFilterHelperImpl jwtFilterHelper = new JwtFilterHelperImpl(mockedJwtRepository, mockJwtDecode, mockMessageService, userRepository);

    /**
     * Given - Mock des données permettant de tester l'implementation de JwtFilterHelperImpl
     */

    // Http - Mock récupération header
    when(mockedHttpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME))
            .thenReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmlhdGV1cjIyQGhvdG1haWwuZnIiLCJqdGkiOiJhNTVhNDMwNS04NTg5LTM0MjYtOGE5YS0wODE4NzY5NDQ1MjMiLCJpc3MiOiJjdG91dHdlYiIsImV4cCI6MTczOTM5NTQ2MSwiaWQiOjEzLCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdfQ.zqPz4FPZ_Nvv9euvhlcf0wKC3T8oTgsxXt3vB0uGNZo");

    // JWT - Mock le decodage d'un JWT
    Claim mockClaim = mock(Claim.class);
    when(mockClaim.asLong()).thenReturn(1L);
    when(mockedDecodedJWT.getClaim("userId")).thenReturn(mockClaim);
    when(mockedDecodedJWT.getToken()).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmlhdGV1cjIyQGhvdG1haWwuZnIiLCJqdGkiOiJhNTVhNDMwNS04NTg5LTM0MjYtOGE5YS0wODE4NzY5NDQ1MjMiLCJpc3MiOiJjdG91dHdlYiIsImV4cCI6MTczOTM5NTQ2MSwiaWQiOjEzLCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdfQ.zqPz4FPZ_Nvv9euvhlcf0wKC3T8oTgsxXt3vB0uGNZo");
    when(mockedDecodedJWT.getId()).thenReturn("a55a4305-8589-3426-8a9a-081876944523");

    // IJwtDecode - Mock la méthode permettant de décoder un JWT en renvoyant le mock JWT
    when(mockJwtDecode.validateAndDecode(any())).thenReturn(mockedDecodedJWT);

    // JwtRepository - Mock la récupération en base donnée du JWT
    when(mockedJwtRepository.findOneByUser(any())).thenReturn(Optional.of(getJwtWithData()));

    /**
     * When - Execution des méthodes chainés dans l'implémentation JwtFilterHelperImpl;
     */

    Optional<DecodedJWT> actualDecodedJWTOptional = jwtFilterHelper
            .extractJwtFromHeaders(mockedHttpServletRequest)
            .decodeJwt()
            .isJwtValid()
            .getDecodedJwt();

    /**
     * Then - Vérification des données
     */
    String expectedTokenFromHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmlhdGV1cjIyQGhvdG1haWwuZnIiLCJqdGkiOiJhNTVhNDMwNS04NTg5LTM0MjYtOGE5YS0wODE4NzY5NDQ1MjMiLCJpc3MiOiJjdG91dHdlYiIsImV4cCI6MTczOTM5NTQ2MSwiaWQiOjEzLCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdfQ.zqPz4FPZ_Nvv9euvhlcf0wKC3T8oTgsxXt3vB0uGNZo";
    String expectedJwtId = "a55a4305-8589-3426-8a9a-081876944523";
    DecodedJWT actualDecodedJWT = actualDecodedJWTOptional.get();

    assertTrue(jwtFilterHelper.getJwtValid());
    assertEquals(expectedTokenFromHeader, jwtFilterHelper.getTokenFromHeader());
    assertNotNull(actualDecodedJWT);
  }
  @Test
  public void jwt_should_not_be_valid_when_bearer_have_no_value() {
    /**
     * Given - Mock des données permettant de tester l'implementation de JwtFilterHelperImpl
     */
    // Http - Mock récupération header
    when(mockedHttpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME))
            .thenReturn("Bearer")
            .thenReturn(null)
            .thenReturn("");


    /**
     * When cas d'un bearer sans données - Execution des méthodes chainés dans l'implémentation JwtFilterHelperImpl;
     */
    JwtFilterHelperImpl jwtFilterHelper1 = new JwtFilterHelperImpl(mockedJwtRepository, mockJwtDecode, mockMessageService, userRepository);
    Optional<DecodedJWT> actualDecodedJWTOptional1 = jwtFilterHelper1
            .extractJwtFromHeaders(mockedHttpServletRequest)
            .decodeJwt()
            .isJwtValid()
            .getDecodedJwt();

    /**
     * When heraderToken null;
     */
    JwtFilterHelperImpl jwtFilterHelper2 = new JwtFilterHelperImpl(mockedJwtRepository, mockJwtDecode, mockMessageService, userRepository);
    Optional<DecodedJWT> actualDecodedJWTOptional2 = jwtFilterHelper2
            .extractJwtFromHeaders(mockedHttpServletRequest)
            .decodeJwt()
            .isJwtValid()
            .getDecodedJwt();

    /**
     * When heraderToken empty string;
     */
    JwtFilterHelperImpl jwtFilterHelper3 = new JwtFilterHelperImpl(mockedJwtRepository, mockJwtDecode, mockMessageService, userRepository);
    Optional<DecodedJWT> actualDecodedJWTOptional3 = jwtFilterHelper3
            .extractJwtFromHeaders(mockedHttpServletRequest)
            .decodeJwt()
            .isJwtValid()
            .getDecodedJwt();

    /**
     * Then - Vérification actualDecodedJWTOptional1
     */
    assertNull(jwtFilterHelper1.getTokenFromHeader());
    assertNull(jwtFilterHelper1.getDecodedJWT());
    assertFalse(jwtFilterHelper1.getJwtValid());
    assertTrue(actualDecodedJWTOptional1.isEmpty());

    /**
     * Then - Vérification actualDecodedJWTOptional2
     */
    assertNull(jwtFilterHelper2.getTokenFromHeader());
    assertNull(jwtFilterHelper2.getDecodedJWT());
    assertFalse(jwtFilterHelper2.getJwtValid());
    assertTrue(actualDecodedJWTOptional2.isEmpty());

    /**
     * Then - Vérification actualDecodedJWTOptional3
     */
    assertNull(jwtFilterHelper3.getTokenFromHeader());
    assertNull(jwtFilterHelper3.getDecodedJWT());
    assertFalse(jwtFilterHelper3.getJwtValid());
    assertTrue(actualDecodedJWTOptional3.isEmpty());
  }
  @Test
  public void jwt_should_throw_when_when_bearer_is_bad_formated() {

    Factory factory = new Factory();
    JwtServiceImpl jwtDecode = new JwtServiceImpl(factory, mockedJwtRepository);
    JwtFilterHelperImpl jwtFilterHelper = new JwtFilterHelperImpl(mockedJwtRepository, jwtDecode, mockMessageService, userRepository);

    ReflectionTestUtils.setField(jwtDecode, "jwtSecret", "dsdsdd24qfsf43sdds34d4fdsf");

    /**
     * Given - Mock des données permettant de tester l'implementation de JwtFilterHelperImpl
     */
    // Http - Mock récupération header
    when(mockedHttpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME))
            .thenReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmlhd");

    when(mockMessageService.getMessage("jwt.unvalid")).thenReturn("Votre session à expirée. Merci de vous reconnecter");


    /**
     * When cas d'un bearer avec des données fausses - Execution des méthodes chainés dans l'implémentation JwtFilterHelperImpl;
     */
    Exception exception = assertThrows(AuthException.class, ()-> jwtFilterHelper
            .extractJwtFromHeaders(mockedHttpServletRequest)
            .decodeJwt()
            .isJwtValid()
            .getDecodedJwt());

    /**
     * Then - Vérification actualDecodedJWTOptional2
     */
    assertEquals("Votre session à expirée. Merci de vous reconnecter", exception.getMessage());

  }
  public JwtEntity getJwtWithData() {
    JwtEntity jwtEntity = new JwtEntity();
    jwtEntity.setEmail("aviateur22@hotmail.fr");
    jwtEntity.setJwtId("a55a4305-8589-3426-8a9a-081876944523");
    jwtEntity.setId(1L);
    jwtEntity.setJwtToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhdmlhdGV1cjIyQGhvdG1haWwuZnIiLCJqdGkiOiJhNTVhNDMwNS04NTg5LTM0MjYtOGE5YS0wODE4NzY5NDQ1MjMiLCJpc3MiOiJjdG91dHdlYiIsImV4cCI6MTczOTM5NTQ2MSwiaWQiOjEzLCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdfQ.zqPz4FPZ_Nvv9euvhlcf0wKC3T8oTgsxXt3vB0uGNZo");
    jwtEntity.setIsValid(true);
    return jwtEntity;
  }


}
