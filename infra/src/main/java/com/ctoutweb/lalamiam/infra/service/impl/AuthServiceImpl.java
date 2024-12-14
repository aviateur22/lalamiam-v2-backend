package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;
import com.ctoutweb.lalamiam.infra.service.IAuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

  private final IApiLanguage apiLanguage;
  private final Factory factory;

  public AuthServiceImpl(IApiLanguage apiLanguage, Factory factory) {
    this.apiLanguage = apiLanguage;
    this.factory = factory;
  }

  @Override
  public void registerClient() {

  }

  @Override
  public void registerProfessional() {

  }

  @Override
  public IAppParam getAppParamter() {
    return factory.getAppParamImpl(apiLanguage.getValidatedLanguage());
  }
}
