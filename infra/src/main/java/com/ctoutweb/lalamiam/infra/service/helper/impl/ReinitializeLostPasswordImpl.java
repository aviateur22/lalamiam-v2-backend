package com.ctoutweb.lalamiam.infra.service.helper.impl;

import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.service.helper.IReinitializeLostPassword;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ReinitializeLostPasswordImpl implements IReinitializeLostPassword {
  private final IMessageService messageService;
  private final ITokenRepository tokenRepository;
  private final IUserRepository userRepository;
  private final ICryptoService cryptoService;
  private TokenEntity tokenFromDatabase;
  private UserEntity user;
  private boolean isFrontEndTokenValid = false;
  public ReinitializeLostPasswordImpl(IMessageService messageService, ITokenRepository tokenRepository, IUserRepository userRepository, ICryptoService cryptoService, Factory factory) {
    this.messageService = messageService;
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
    this.cryptoService = cryptoService;
  }
  @Override
  public IReinitializeLostPassword findUserToken(String email) {
    user = userRepository.findByEmail(email).orElse(null);

    if(user == null)
      throw new BadRequestException(messageService.getMessage("only.email.unvalid"));

    this.tokenFromDatabase = tokenRepository
            .findFirstByUserAndCryptographyTypeOrderByIdDesc(user, CryptographyType.ENCRYPT.toString())
            .orElse(null);

    if(tokenFromDatabase == null)
        throw new BadRequestException(messageService.getMessage("change.password.error"));

    return this;
  }

  @Override
  public IReinitializeLostPassword isFrontEndTokenValid(String tokenFromFrontEnd) {
    byte[] iv = Base64.getDecoder().decode(tokenFromDatabase.getIvKey().getBytes());

    String decryptTokenFromDatabase = cryptoService.decrypt(
            tokenFromDatabase.getCryptographyText(),iv);

    String decryptTokenFromFrontEnd = cryptoService.decrypt(
            tokenFromFrontEnd, iv);

    if(!decryptTokenFromDatabase.equals(decryptTokenFromFrontEnd))
      throw new BadRequestException(messageService.getMessage("change.password.error"));

    this.isFrontEndTokenValid = true;
    return this;
  }

  @Override
  public void updatePassword(String password) {
   String hashNewPassword = cryptoService.hashText(password);
    user.setPassword(hashNewPassword);
    userRepository.save(user);
  }

  /**
   * Suppression du token
   */
  public IReinitializeLostPassword deleteUserToken() {
    if(this.isFrontEndTokenValid)
      tokenRepository.delete(tokenFromDatabase);

    return this;
  }
}
