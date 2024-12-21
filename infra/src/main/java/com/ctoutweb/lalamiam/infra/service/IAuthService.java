package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;

public interface IAuthService {
  public void registerClient(RegisterClientDto dto);

  public void registerProfessional();

  public IAppParam getAppParamter();
}
