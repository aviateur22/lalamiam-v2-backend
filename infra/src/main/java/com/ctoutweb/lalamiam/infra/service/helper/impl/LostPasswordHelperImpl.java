package com.ctoutweb.lalamiam.infra.service.helper.impl;

import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IEmailService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.service.helper.EmailHelper;
import com.ctoutweb.lalamiam.infra.service.helper.FrontEndLinkHelper;
import com.ctoutweb.lalamiam.infra.service.helper.ILostPasswordHelper;
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

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.LOST_PASSWORD_TOKEN_VALIDITY;

@Component
public class LostPasswordHelperImpl implements ILostPasswordHelper {
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
  private TokenEntity encryptUrlTokenSaved; // Donnée apres sauvgarde du chiffrement du token de l'url

  public LostPasswordHelperImpl(ICryptoService cryptoService, IMessageService messageService, IEmailService emailService, ITokenRepository tokenRepository, IUserRepository userRepository, EmailHelper emailHelper, FrontEndLinkHelper frontEndLinkHelper) {
    this.cryptoService = cryptoService;
    this.messageService = messageService;
    this.emailService = emailService;
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
    this.emailHelper = emailHelper;
    this.frontEndLinkHelper = frontEndLinkHelper;
  }

  @Override
  public ILostPasswordHelper findUserInformation(String email) {
    // Recherche utilisateur
    this.user = userRepository.findByEmail(email).orElse(null);

    if(this.user == null)
      throw new BadRequestException(messageService.getMessage("only.email.unvalid"));

    return this;
  }

  @Override
  public ILostPasswordHelper generateRenewalPasswordToken() {
    plainTextUrlToken = TextUtility.generateRandomText(75);
    return this;
  }

  @Override
  public ILostPasswordHelper encryptToken() {
    // Clé iv pour chiffrement
    byte[] iv = cryptoService.generateRandomByte();
    ivKeyStringFormat = Base64.getEncoder().encodeToString(iv);
    this.encryptUrlToken = cryptoService.encrypt(plainTextUrlToken, iv);
    return this;
  }

  @Override
  public ILostPasswordHelper saveGeneratedEncryptTokenWithUser() {
    if(user == null)
      return this;

    TokenEntity tokenEntity = new TokenEntity();
    tokenEntity.setIvKey(ivKeyStringFormat);
    tokenEntity.setCryptographyText(encryptUrlToken);
    tokenEntity.setCryptographyType(CryptographyType.ENCRYPT.toString());
    tokenEntity.setValidUntil(ZonedDateTime.of(LocalDateTime.now().plusHours(LOST_PASSWORD_TOKEN_VALIDITY), ZoneId.of(zoneId)));
    tokenEntity.setUser(user);
    this.encryptUrlTokenSaved = tokenRepository.save(tokenEntity);

    return this;
  }

  @Override
  public void sendLostPasswordEmail(String userEmail) {
    if(user == null)
      return;

    // Generation d'un email
    String frontUrl = frontEndLinkHelper.getUserLostPasswordLink(userEmail, encryptUrlToken);
    Map<String, String> listOfWordCaseLostPassword = emailHelper.listOfWordToReplaceCaseLostPasswordEmail(
            userEmail,
            frontUrl
    );

    emailService
            .setEmailInformation(HtmlTemplateType.CHANGE_PASSWORD, userEmail)
            .replaceWordInHtmlTemplate(HtmlTemplateType.CHANGE_PASSWORD, listOfWordCaseLostPassword)
            .sendEmail();
  }
}
