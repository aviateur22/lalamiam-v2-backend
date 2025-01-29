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

  /**
   * Login
   * @param dto LoginDto
   * @return LoginResponseDto
   */
  public LoginResponseDto login(LoginDto dto);

  /***
   * Déconnexion
   * @param dto LogoutDto
   * @return IMessageResponse
   */
  public IMessageResponse logout(LogoutDto dto);

  /**
   * Généaration des données de l'application
   * -> recupération du language API
   * -> Token pour génération CSRF (Cookies)
   * -> Token pour la gnération de captcha (Cookie)
   * @return IAppParam
   */
  public IAppParam getAppParamter();
}
