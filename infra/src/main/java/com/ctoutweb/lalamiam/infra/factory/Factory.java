package com.ctoutweb.lalamiam.infra.factory;


import com.ctoutweb.lalamiam.infra.controller.validation.impl.DoubleDtoToValidateImpl;
import com.ctoutweb.lalamiam.infra.controller.validation.impl.DtoToValidateImpl;
import com.ctoutweb.lalamiam.infra.controller.validation.IDtoToValidate;
import com.ctoutweb.lalamiam.infra.model.*;
import com.ctoutweb.lalamiam.infra.model.auth.IProfessionalRegisterToken;
import com.ctoutweb.lalamiam.infra.model.auth.IRegisterFile;
import com.ctoutweb.lalamiam.infra.model.auth.impl.ProfessionalRegisterToken;

import com.ctoutweb.lalamiam.infra.model.auth.impl.RegisterFileImpl;
import com.ctoutweb.lalamiam.infra.model.param.AppParamImpl;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptchaImage;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.impl.CaptchaImageImpl;
import com.ctoutweb.lalamiam.infra.model.captcha.impl.CaptchaImpl;
import com.ctoutweb.lalamiam.infra.model.image.IImageBase64;
import com.ctoutweb.lalamiam.infra.model.image.IPropertiesImage;
import com.ctoutweb.lalamiam.infra.model.image.impl.ImageBase64Impl;
import com.ctoutweb.lalamiam.infra.model.image.impl.ImagePropertiesImpl;
import com.ctoutweb.lalamiam.infra.model.image.MimeType;
import com.ctoutweb.lalamiam.infra.model.impl.*;
import com.ctoutweb.lalamiam.infra.model.security.CaptchaTokenImpl;
import com.ctoutweb.lalamiam.infra.model.security.ICaptchaToken;
import com.ctoutweb.lalamiam.infra.repository.entity.TokenEntity;
import com.ctoutweb.lalamiam.infra.security.authentication.UserPrincipal;
import com.ctoutweb.lalamiam.infra.security.csrf.CsrfTokenImpl;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.security.jwt.impl.JwtIssueImpl;
import com.ctoutweb.lalamiam.infra.service.ICryptoService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;

@Component
public class Factory {
  public IApiLanguage getImpl(String language) {
    return new ApiLanguageImpl(language);
  }

  public IApiMessage getImpl(Properties properties) {
    return new ApiMessageImpl(properties);
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

  public CsrfToken getCsrfTokenImpl(String headerName, String parameterName, String token) {
    return new CsrfTokenImpl(headerName, parameterName, token);
  }

  public IJwtIssue getImpl(String jwtId, String jwtToken, ZonedDateTime expiredAt) {
    return new JwtIssueImpl(jwtId, jwtToken, expiredAt);
  }

  public IPropertiesImage getImpl(String name, MimeType type) {
    return new ImagePropertiesImpl(name, type);
  }

  public ICaptcha getImpl(String title, IImageBase64 imageBase64, String captchaResponseIdEncrypt) {
    return new CaptchaImpl(title, imageBase64, captchaResponseIdEncrypt);
  }

  public IImageBase64 getImpl(String mimeTye, String imageBase64) {
    return new ImageBase64Impl(mimeTye, imageBase64);
  }

  public ICaptchaImage getImpl(Long id, String name, String path, String response) {
    return new CaptchaImageImpl(id, name, path, response);
  }
  public IAppParam getAppParamImpl(String language) {
    return new AppParamImpl(language);
  }

  public IErrorMessage getErrorMessageImpl(String errorMessage) {
    return new ErrorMessageImpl(errorMessage);
  }

  public ICaptchaToken getImpl(TokenEntity captchaTokenEntity, String captchaResponseByUser, ICryptoService cryptoService) {
    return new CaptchaTokenImpl(cryptoService, captchaTokenEntity, captchaResponseByUser);
  }
  public IUserLoginStatus getImpl(boolean isLoginAuthorize, ZonedDateTime recoveryLoginTime) {
    return new UserLoginStatusImpl(isLoginAuthorize, recoveryLoginTime);
  }
  public IProfessionalRegisterToken getProfessionalRegisterTokenImpl(String encryptUrlToken, String plainTextEmailToken) {
    return new ProfessionalRegisterToken(encryptUrlToken, plainTextEmailToken) ;
  }

  public IRegisterFile getRegisterFileImpl(InputStream registerFile, Long fileSize, String fileExtension) {
    return new RegisterFileImpl(registerFile, fileSize, fileExtension);
  }

  public UserPrincipal getUserPrincipal(Long id, String email, String password, boolean isAccountActive, List<SimpleGrantedAuthority> authorities) {
    UserPrincipal userPrincipal = new UserPrincipal();
    userPrincipal.setId(id);
    userPrincipal.setEmail(email);
    userPrincipal.setPassword(password);
    userPrincipal.setIsAccountActive(isAccountActive);
    userPrincipal.setAuthorities(authorities);
    return  userPrincipal;
  }
}
