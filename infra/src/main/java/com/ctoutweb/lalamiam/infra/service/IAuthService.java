package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.dto.*;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;

public interface IAuthService {
  public void registerClient(RegisterClientDto dto);

  public void registerProfessional(RegisterProfessionalDto dto);

  /**
   * Confirmation d'une création de compte pro par le profesionel
   * @param dto RegisterConfirmByProfessionalDto
   * @return IMessageResponse
   */
  public IMessageResponse registerConfirmByProfessional(RegisterConfirmByProfessionalDto dto);
  public LoginResponseDto login(LoginDto dto);

  public IAppParam getAppParamter();
}
