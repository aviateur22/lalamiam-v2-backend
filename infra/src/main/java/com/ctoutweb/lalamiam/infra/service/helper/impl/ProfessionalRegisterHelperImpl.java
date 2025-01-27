package com.ctoutweb.lalamiam.infra.service.helper.impl;

import com.ctoutweb.lalamiam.infra.helper.EmailHelper;
import com.ctoutweb.lalamiam.infra.helper.FrontEndLinkHelper;
import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IEmailService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.service.helper.IProfessionalRegisterHelper;
import com.ctoutweb.lalamiam.infra.utility.TextUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Map;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.LIMIT_DAY_TO_VALIDATE_INSCRIPTION;

@Component
public class ProfessionalRegisterHelperImpl implements IProfessionalRegisterHelper {
  private static final Logger LOGGER = LogManager.getLogger();
  @Value("${zone.id}")
  String zoneId;
  private final ICryptoService cryptoService;
  private final IMessageService messageService;
  private final IEmailService emailService;
  private final ITokenRepository tokenRepository;
  private final IUserRepository userRepository;
  private final EmailHelper emailHelper;
  private final FrontEndLinkHelper frontEndLinkHelper;
  private UserEntity user; // Utilisateur créant un compte pro
  private String plainTextUrlToken;  // Token qui sera inclu dans URL de l'email
  private String encryptUrlToken;   // chiffrage du token qui sera inclu dans URL de l'email
  private String ivKeyStringFormat; // Clé de chiffrement
  private String plainTextEmailToken; // Token contenu dans l'email
  private String hashEmailToken; // Hash du token de l'email
  private TokenEntity hashEmailTokenSaved; // Donnée apres sauvgarde du HASH token de l'email
  private TokenEntity encryptUrlTokenSaved; // Donnée apres sauvgarde du chiffrement du token de l'url


  public ProfessionalRegisterHelperImpl(
          ICryptoService cryptoService,
          IMessageService messageService,
          IEmailService emailService,
          ITokenRepository tokenRepository,
          IUserRepository userRepository,
          EmailHelper emailHelper,
          FrontEndLinkHelper frontEndLinkHelper) {
    this.cryptoService = cryptoService;
    this.messageService = messageService;
    this.emailService = emailService;
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
    this.emailHelper = emailHelper;
    this.frontEndLinkHelper = frontEndLinkHelper;
  }
  @Override
  public IProfessionalRegisterHelper findProfessionalInformation(String email) {
    // Recherche utilisateur
    this.user = userRepository.findByEmail(email).orElse(null);
    return this;
  }

  @Override
  public IProfessionalRegisterHelper generateTokenForProfessionalRegister() {
    plainTextEmailToken = TextUtility.generateRandomText(5);
    plainTextUrlToken = TextUtility.generateRandomText(55);

    return this;
  }
  @Override
  public IProfessionalRegisterHelper tokenCryptography() {
    // Hash du token contenu de le corp de l'email
    this.hashEmailToken = cryptoService.hashText(plainTextEmailToken);

    // Chiffrement du token prevu dans URL de l'email
    encryptUrlToken();

    return this;
  }

  /**
   * Chiffrement du Token de l'url
   */
  public void encryptUrlToken() {
    // Clé iv pour chiffrement
    byte[] iv = cryptoService.generateRandomByte();
    ivKeyStringFormat = Base64.getEncoder().encodeToString(iv);
    this.encryptUrlToken = cryptoService.encrypt(plainTextUrlToken, iv);

  }

  @Override
  public IProfessionalRegisterHelper saveGeneratedHashTokenWithUser() {
    if(user == null)
      return this;

    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setIvKey(null);
    tokenEntity.setCryptographyText(hashEmailToken);
    tokenEntity.setCryptographyType(CryptographyType.HASH.toString());
    tokenEntity.setValidUntil(ZonedDateTime.of(LocalDateTime.now().plusHours(LIMIT_DAY_TO_VALIDATE_INSCRIPTION), ZoneId.of(zoneId)));
    tokenEntity.setUser(user);
    this.hashEmailTokenSaved = tokenRepository.save(tokenEntity);
    return this;
  }

  @Override
  public IProfessionalRegisterHelper saveGeneratedEncryptTokenWithUser() {
    if(user == null)
      return this;

    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setIvKey(ivKeyStringFormat);
    tokenEntity.setCryptographyText(encryptUrlToken);
    tokenEntity.setCryptographyType(CryptographyType.ENCRYPT.toString());
    tokenEntity.setValidUntil(ZonedDateTime.of(LocalDateTime.now().plusHours(LIMIT_DAY_TO_VALIDATE_INSCRIPTION), ZoneId.of(zoneId)));
    tokenEntity.setUser(user);
    this.encryptUrlTokenSaved = tokenRepository.save(tokenEntity);

    return this;
  }

  @Override
  public void sendRegisterEmail(String professionalEmail) {
    if(user == null)
      return;

    // Generation d'un email
    String frontUrl = frontEndLinkHelper.getProfessionalConfirmationRegisterLink(professionalEmail, encryptUrlToken);
    Map<String, String> listOfWordCaseRegisterConfirmation = emailHelper.listOfWordCaseRegisterConfirmation(
            professionalEmail,
            plainTextEmailToken,
            frontUrl
    );

    emailService
            .setEmailInformation(HtmlTemplateType.PROFESSIONAL_REGISTER_ACCOUNT_CONFIRMATION, professionalEmail)
            .replaceWordInHtmlTemplate(HtmlTemplateType.PROFESSIONAL_REGISTER_ACCOUNT_CONFIRMATION, listOfWordCaseRegisterConfirmation)
            .sendEmail();
  }
}
