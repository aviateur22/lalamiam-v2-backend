package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.dto.LoginDto;
import com.ctoutweb.lalamiam.infra.dto.LoginResponseDto;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;

public interface IAuthService {
  public void registerClient(RegisterClientDto dto);

  public void registerProfessional(RegisterProfessionalDto dto);
  public LoginResponseDto login(LoginDto dto);

  public IAppParam getAppParamter();
}
