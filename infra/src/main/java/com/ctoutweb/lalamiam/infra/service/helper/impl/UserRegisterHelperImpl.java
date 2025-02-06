package com.ctoutweb.lalamiam.infra.service.helper.impl;

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
import com.ctoutweb.lalamiam.infra.service.helper.IUserRegisterHelper;
import com.ctoutweb.lalamiam.infra.utility.TextUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Map;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.LIMIT_DAY_TO_VALIDATE_INSCRIPTION;

@Component
public class UserRegisterHelperImpl implements IUserRegisterHelper {
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

  public UserRegisterHelperImpl(
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
  public IUserRegisterHelper findUserInformation(String email) {
    // Recherche utilisateur
    this.user = userRepository.findByEmail(email).orElse(null);
    return this;
  }

  @Override
  public IUserRegisterHelper generateTokenRegistration() {
    plainTextUrlToken = TextUtility.generateRandomTextBetween(45, 65);
    return this;
  }

  @Override
  public IUserRegisterHelper tokenCryptography() {
    // Clé iv pour chiffrement
    byte[] iv = cryptoService.generateRandomByte();
    ivKeyStringFormat = Base64.getEncoder().encodeToString(iv);
    this.encryptUrlToken = cryptoService.encrypt(plainTextUrlToken, iv);

    return this;
  }

  @Override
  public IUserRegisterHelper saveGeneratedEncryptTokenWithUser() {
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
    String frontUrl = frontEndLinkHelper.getUserActivateAccountLink(professionalEmail, encryptUrlToken);
    Map<String, String> listOfWordCaseRegisterConfirmation = emailHelper.listOfWordCaseUserActivateAccount(
            professionalEmail,
            frontUrl
    );

    emailService
            .setEmailInformation(HtmlTemplateType.USER_ACCOUNT_ACTIVATION, professionalEmail)
            .replaceWordInHtmlTemplate(HtmlTemplateType.USER_ACCOUNT_ACTIVATION, listOfWordCaseRegisterConfirmation)
            .sendEmail();
  }
}
