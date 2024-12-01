package com.ctoutweb.lalamiam.infra.factory;

import com.ctoutweb.lalamiam.infra.controller.validation.impl.DoubleDtoToValidateImpl;
import com.ctoutweb.lalamiam.infra.controller.validation.impl.DtoToValidateImpl;
import com.ctoutweb.lalamiam.infra.controller.validation.IDtoToValidate;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.IApiMessage;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.model.captcha.BaseCapatcha;
import com.ctoutweb.lalamiam.infra.model.image.IPropertiesImage;
import com.ctoutweb.lalamiam.infra.model.image.impl.ImagePropertiesImpl;
import com.ctoutweb.lalamiam.infra.model.image.MimeType;
import com.ctoutweb.lalamiam.infra.model.impl.*;
import com.ctoutweb.lalamiam.infra.repository.ITokenRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.RoleUserEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserAccountEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedClient;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedRole;
import com.ctoutweb.lalamiam.core.entity.clientInscription.IClientInscription.ICreatedAccount;
import com.ctoutweb.lalamiam.infra.security.csrf.CsrfHeaderTokenImpl;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.security.jwt.impl.JwtIssueImpl;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Properties;

@Component
public class Factory {


  public IApiLanguage getImpl(String language) {
    return new ApiLanguageImpl(language);
  }

  public IApiMessage getImpl(Properties properties) {
    return new ApiMessageImpl(properties);
  }

  public ICreatedClient getImlpl(UserEntity user) {
    return new CreatedClientImpl(user);
  }

  public ICreatedRole getImpl(RoleUserEntity roleUser) {
    return new CreatedRoleImpl(roleUser);
  }

  public ICreatedAccount getImpl(UserAccountEntity userAccount)  {
    return new CreatedAccountImpl(userAccount);
  }

  public IMessageResponse getMessageResponseImpl(String messageResponse) {
    return new MessageResponseImpl(messageResponse);
  }

  public<T> IDtoToValidate getImpl(T dto1, T dto2) {
    return new DoubleDtoToValidateImpl<>(dto1, dto2);
  }
  public<T> IDtoToValidate getImpl(T dto1) {
    return new DtoToValidateImpl<>(dto1);
  }

  public CsrfToken getCsrfHeaderTokenImpl(String headerName, String parameterName, String token) {
    return new CsrfHeaderTokenImpl(headerName, parameterName, token);
  }

  public CsrfToken getCsrfCookieTokenImpl(String headerName, String parameterName, String token) {
    return new CsrfHeaderTokenImpl(headerName, parameterName, token);
  }

  public IJwtIssue getImpl(String jwtId, String jwtToken, ZonedDateTime expiredAt) {
    return new JwtIssueImpl(jwtId, jwtToken, expiredAt);
  }
  public IPropertiesImage getImpl(String name, MimeType type) {
    return new ImagePropertiesImpl(name, type);
  }

  public BaseCapatcha getImpl(IMessageService messageService, ITokenRepository tokenRepository, ICryptoService cryptoService) {
    return new BaseCapatcha(messageService, tokenRepository, cryptoService);
  }

}
