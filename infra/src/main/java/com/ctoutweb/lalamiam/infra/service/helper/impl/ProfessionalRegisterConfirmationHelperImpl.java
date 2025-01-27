package com.ctoutweb.lalamiam.infra.service.helper.impl;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.security.CryptographyType;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.IUserRepository;
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
  private final Factory factory;

  private TokenEntity tokenFromDatabase;

  public ProfessionalRegisterConfirmationHelperImpl(
          IMessageService messageService,
          ITokenRepository tokenRepository,
          IUserRepository userRepository,
          ICryptoService cryptoService,
          Factory factory) {
    this.messageService = messageService;
    this.tokenRepository = tokenRepository;
    this.userRepository = userRepository;
    this.cryptoService = cryptoService;
    this.factory = factory;
  }

  @Override
  public IProfessionalRegisterConfirmationHelper findUserRegisterToken(String email, CryptographyType cryptographyType) {
    UserEntity user = userRepository.findByEmail(email).orElse(null);

    if(user == null)
      return null;

    TokenEntity token = tokenRepository.findFirstByUserAndCryptographyTypeOrderByIdDesc(user, cryptographyType.toString()).orElse(null);

    this.tokenFromDatabase = token;

    if(tokenFromDatabase != null)
      deleteUserToken(token);

    return this;
  }

  /**
   * Suppression d'un Token en base de donnnées
   * @param tokenFromDatabase TokenEntity
   */

  private void deleteUserToken(TokenEntity tokenFromDatabase) {
    tokenRepository.delete(tokenFromDatabase);
  }

  @Override
  public Boolean isFrontEndTokenValid(String tokenFromFrontEnd) {
    if(this.tokenFromDatabase == null)
      return false;

    CryptographyType cryptographyType = CryptographyType.findCryptotype(tokenFromDatabase.getCryptographyType());

    if(cryptographyType == null)
      return false;

    return switch (cryptographyType) {
      case ENCRYPT ->  isEncryptTokenValid(tokenFromDatabase, tokenFromFrontEnd);
      case HASH-> cryptoService.isHashValid(tokenFromFrontEnd, tokenFromDatabase.getCryptographyText());
    };
  }

  /**
   * Vérification si un token de type "Encrypt" est valide   *
   * Pour info, la clé IV utilisé pour déchiffrer tokenFromFrontEnd sera celle provenant du tokenFromDatabase
   * @param tokenFromDatabase TokenEntity - Token provenant de la base de données
   * @param tokenFromFrontEnd String - Token provenant du frontEnd
   * @return boolean
   */
  public boolean isEncryptTokenValid(TokenEntity tokenFromDatabase, String tokenFromFrontEnd) {
    byte[] iv = Base64.getDecoder().decode(tokenFromDatabase.getIvKey().getBytes());

    String decryptTokenFromDatabase = cryptoService.decrypt(
            tokenFromDatabase.getCryptographyText(),iv);

    String decryptTokenFromFrontEnd = cryptoService.decrypt(
            tokenFromFrontEnd, iv);

    return decryptTokenFromDatabase.equals(decryptTokenFromFrontEnd);
  }
}
