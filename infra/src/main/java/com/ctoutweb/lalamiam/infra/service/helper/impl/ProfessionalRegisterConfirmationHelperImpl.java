package com.ctoutweb.lalamiam.infra.service.helper.impl;

import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.service.helper.IProfessionalRegisterConfirmationHelper;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ProfessionalRegisterConfirmationHelperImpl implements IProfessionalRegisterConfirmationHelper {
  private final IMessageService messageService;
  private final ITokenRepository tokenRepository;
  private final IUserRepository userRepository;
  private final ICryptoService cryptoService;
  private TokenEntity urlTokenFromDatabase;
  private TokenEntity emailTokenFromDatabase;
  private boolean isUrlTokenValid = false;
  private boolean isEmailTokenValid = false;


  public ProfessionalRegisterConfirmationHelperImpl(
          IMessageService messageService,
          ITokenRepository tokenRepository,
          IUserRepository userRepository,
          ICryptoService cryptoService) {
    this.messageService = messageService;
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
    this.cryptoService = cryptoService;
  }

  @Override
  public IProfessionalRegisterConfirmationHelper findUserRegisterToken(String email) {
    UserEntity user = userRepository.findByEmail(email).orElse(null);

    if(user == null)
      throw new BadRequestException(messageService.getMessage("professional.not.find"));

    if(user.getProfessionalInformation() == null)
      throw new BadRequestException(messageService.getMessage("professional.account.confirmed.error"));

    if(user.getProfessionalAccountInformation().getIsAccountRegisterConfirmByProfessional())
      throw new BadRequestException(messageService.getMessage("professional.account.already.confirmed"));

    // Récupération urlToken de la base de donnnées
    urlTokenFromDatabase = tokenRepository
            .findFirstByUserAndCryptographyTypeOrderByIdDesc(user, CryptographyType.ENCRYPT.toString())
            .orElse(null);

    // Récupération emailToken de la base de donnnées
    emailTokenFromDatabase = tokenRepository
            .findFirstByUserAndCryptographyTypeOrderByIdDesc(user, CryptographyType.HASH.toString())
            .orElse(null);

    return this;
  }

  @Override
  public IProfessionalRegisterConfirmationHelper isEmailTokenValid(String emailTokenFromFrontEnd) {
    if(this.emailTokenFromDatabase == null)
      throw new BadRequestException("professional.account.confirmed.token.error");

    this.isEmailTokenValid = cryptoService.isHashValid(emailTokenFromFrontEnd, emailTokenFromDatabase.getCryptographyText());
    return this;
  }

  @Override
  public IProfessionalRegisterConfirmationHelper isUrlTokenValid(String urlTokenFromFrontEnd) {
    if(this.urlTokenFromDatabase == null)
      throw new BadRequestException("professional.account.confirmed.token.error");

    byte[] iv = Base64.getDecoder().decode(urlTokenFromDatabase.getIvKey().getBytes());

    String decryptTokenFromDatabase = cryptoService.decrypt(
            urlTokenFromDatabase.getCryptographyText(),iv);

    String decryptTokenFromFrontEnd = cryptoService.decrypt(
            urlTokenFromFrontEnd, iv);

    isUrlTokenValid = decryptTokenFromDatabase.equals(decryptTokenFromFrontEnd);

    return this;
  }

  @Override
  public IProfessionalRegisterConfirmationHelper deleteTokenOnSuccess() {
    if(this.areTokensValid()) {
      tokenRepository.delete(urlTokenFromDatabase);
      tokenRepository.delete(emailTokenFromDatabase);
    }
    return this;
  }

  @Override
  public Boolean areTokensValid() {
   return this.isEmailTokenValid && this.isUrlTokenValid;
  }

  /**
   * Suppression d'un Token en base de donnnées
   */
  private void deleteUserToken() {

  }
}
