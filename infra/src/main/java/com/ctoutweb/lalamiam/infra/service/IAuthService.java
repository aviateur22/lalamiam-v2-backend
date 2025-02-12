package com.ctoutweb.lalamiam.infra.service;

import com.ctoutweb.lalamiam.infra.dto.auth.*;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;

public interface IAuthService {
  public IMessageResponse registerClient(RegisterClientDto dto);

  public IMessageResponse registerProfessional(RegisterProfessionalDto dto);

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

  /**
   * Gestion de la perte d'un mot de passe
   * @param dto HandleLostPasswordDto
   * @return IMessageResponse
   */
  public IMessageResponse lostPasswordMailing(HandleLostPasswordDto dto);

  /**
   * Mise à jour d'un mot de passe perdu
   * @param dto ReinitializeLostPasswordDto
   * @return IMessageResponse
   */
  public IMessageResponse reinitializeLostPassword(ReinitializeLostPasswordDto dto);

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
