package com.ctoutweb.lalamiam.infra.helper;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.auth.IProfessionalRegisterToken;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.utility.TextUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.LIMIT_DAY_TO_VALIDATE_INSCRIPTION;

@Component
public class AuthServiceHelper {
  private static final Logger LOGGER = LogManager.getLogger();
  @Value("${captcha.iv.key}")
  String captchaIvKey;

  @Value("${zone.id}")
  String zoneId;
  private final ICryptoService cryptoService;
  private final IMessageService messageService;
  private final ITokenRepository tokenRepository;
  private final IUserRepository userRepository;
  private final Factory factory;

  public AuthServiceHelper(ICryptoService cryptoService, IMessageService messageService, ITokenRepository tokenRepository, IUserRepository userRepository, Factory factory) {
    this.cryptoService = cryptoService;
    this.messageService = messageService;
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
    this.factory = factory;
  }

  /**
   * Génération de Token lors de l'inscription d'un professionel
   *
   */
  public IProfessionalRegisterToken generateTokenForProfessionalRegister(String email) {
    String plainTextEmailToken = TextUtility.generateRandomText(5);
    String plainTextUrlToken = TextUtility.generateRandomText(55);

    // Recherche utilisateur
    UserEntity user = userRepository.findByEmail(email).orElse(null);

    if(user == null)
      return null;

    TokenEntity hashEmailToken = saveGeneratedTokenWithUser(CryptographyType.HASH, plainTextEmailToken, user);
    TokenEntity encryptUrlToken = saveGeneratedTokenWithUser(CryptographyType.ENCRYPT, plainTextUrlToken, user);

    return factory.getProfessionalRegisterTokenImpl(encryptUrlToken.getCryptographyText(), plainTextEmailToken);
  }

  /**
   * Sauvegarde de token
   * @param cryptographyType CryptographyType - Type de cryptographie (Hash, chiffrement)
   * @param plainText String - Text en claire
   * @param user UserEntity - User pour qui le token est généré
   * @return
   */
  public TokenEntity saveGeneratedTokenWithUser(CryptographyType cryptographyType, String plainText, UserEntity user) {
    TokenEntity tokenEntity = new TokenEntity();

    // Clé iv pour chiffrement
    byte[] iv = cryptoService.generateRandomByte();
    String ivStringFormat = Base64.getEncoder().encodeToString(iv);

    String cryptoText = switch (cryptographyType) {
      case HASH -> cryptoService.hashText(plainText);
      case ENCRYPT -> cryptoService.encrypt(plainText, iv);
      default -> null;
    };

    if(cryptoText == null) {
      LOGGER.error(()->String.format("Impossible de produire un HASH ou ENCRYPT le text suivant %s", plainText));
      return null;
    }

    tokenEntity.setIvKey(cryptographyType.equals(CryptographyType.ENCRYPT) ? ivStringFormat : null);
    tokenEntity.setCryptographyText(cryptoText);
    tokenEntity.setCryptographyType(cryptographyType.toString());
    tokenEntity.setValidUntil(ZonedDateTime.of(LocalDateTime.now().plusHours(LIMIT_DAY_TO_VALIDATE_INSCRIPTION), ZoneId.of(zoneId)));
    tokenEntity.setUser(user);

    // Sauvegarde du token
    return tokenRepository.save(tokenEntity);
  }

  /**
   *
   * @param email
   * @return
   */
  public List<TokenEntity> findUserRegisterToken(String email, CryptographyType cryptographyType) {
    UserEntity user = userRepository.findByEmail(email).orElse(null);

    if(user == null)
      return null;

    TokenEntity token = tokenRepository.findFirstByUserAndCryptographyType(user, cryptographyType.toString());
    deleteUserToken(user, cryptographyType);

    return null;
  }

  public void deleteUserToken(UserEntity user, CryptographyType cryptographyType) {
    tokenRepository.deleteByUserAndCryptographyType(user, cryptographyType.toString());
  }

  /**
   * Vérification si un token est valide
   * @param tokenFromDatabase TokenEntity - Token provenant de la base de données
   * @param tokenFromFrontEnd String - Token provenant du frontEnd
   * @return boolean
   */
  public boolean isFrontEndTokenValid(TokenEntity tokenFromDatabase, String tokenFromFrontEnd) {

    CryptographyType cryptographyType = CryptographyType.findCryptotype(tokenFromDatabase.getCryptographyType());

    if(cryptographyType == null)
      return false;

    return switch (cryptographyType) {
       case ENCRYPT ->  isEncryptTokenValid(tokenFromDatabase, tokenFromFrontEnd);       // Handle HASH case

       case HASH-> cryptoService.isHashValid(tokenFromFrontEnd, tokenFromDatabase.getCryptographyText());

    };
  }

  /**
   * Vérification si un token de type "Encrypt" est valide
   * @param tokenFromDatabase TokenEntity - Token provenant de la base de données
   * @param tokenFromFrontEnd String - Token provenant du frontEnd
   * @return boolean
   */
  public boolean isEncryptTokenValid(TokenEntity tokenFromDatabase, String tokenFromFrontEnd) {
    return false;
  }
}
