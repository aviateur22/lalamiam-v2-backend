package com.ctoutweb.lalamiam.infra.controller;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.infra.dto.*;
import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.impl.MessageResponseImpl;
import com.ctoutweb.lalamiam.infra.model.param.IAppParam;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.service.IAuthService;
import com.ctoutweb.lalamiam.infra.service.ISecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final Factory factory;
  private final Validator validator;
  private final IMessageService messageService;
  private final ISecurityService securityService;
  private final IAuthService authService;
  public AuthController(
          Factory factory,
          IMessageService messageService,
          Validator validator,
          ISecurityService configurationService,
          IAuthService authService) {
    this.factory = factory;
    this.messageService = messageService;
    this.validator = validator;
    this.securityService = configurationService;
    this.authService = authService;
  }

  @PostMapping("/register-client")
  ResponseEntity<IMessageResponse> registerClient(@RequestBody RegisterClientDto registerClientDto) {

    // Validation dto
    factory.getImpl(registerClientDto).validateDto(validator);

    // Validation captcha
    if(!securityService.isUserCaptchaResponseValid(registerClientDto.userCaptchaResponse()))
      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));

    // Creation compte client
    authService.registerClient(registerClientDto);

    return new ResponseEntity<>(factory.getMessageResponseImpl(messageService.getMessage("register.success")), HttpStatus.OK);
  }

  @PostMapping(value = "/register-professional", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  ResponseEntity<IMessageResponse> registerProfessional(@ModelAttribute RegisterProfessionalDto registerProfessionalDto) {
    // Validation dto
    factory.getImpl(registerProfessionalDto).validateDto(validator);

    // Validation captcha
    if(!securityService.isUserCaptchaResponseValid(registerProfessionalDto.getUserCaptchaResponse()))
      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));

    // Creation compte client
    authService.registerProfessional(registerProfessionalDto);

    return new ResponseEntity<>(factory.getMessageResponseImpl(messageService.getMessage("register.success")), HttpStatus.OK);
  }

  @PostMapping("/register-confirm-by-professional")
  ResponseEntity<IMessageResponse> registerConfirmByProfessional(@RequestBody RegisterConfirmByProfessionalDto registerConfirmByProfessionalDto) {
    // Validation dto
    factory.getImpl(registerConfirmByProfessionalDto).validateDto(validator);

    IMessageResponse response = authService.registerConfirmByProfessional(registerConfirmByProfessionalDto);
    
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
  @PostMapping("/login")
  ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
    factory.getImpl(loginDto).validateDto(validator);

    LoginResponseDto loginResponseDto = authService.login(loginDto);

    return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
  }

  /**
   * Traitement d'un mot de passe oublié
   * Generation de token + envoi email pour réinitialiser son mot de passe
   * @param dto HandleLostPasswordDto
   */
  @PostMapping("/handle-lost-password")
  public ResponseEntity<IMessageResponse> lostPasswordMailing(@RequestBody HandleLostPasswordDto dto) {

    // Validation des données
    factory.getImpl(dto).validateDto(validator);

    // Validation du control mail + encoie email
    return new ResponseEntity<>(authService.lostPasswordMailing(dto), HttpStatus.OK);

  }

  @PostMapping("/reinitialize-lost-password")
  public ResponseEntity<IMessageResponse> reinitializeLostPassword(@RequestBody ReinitializeLostPasswordDto dto) {
    // Validation du format du mot de passe
    factory.getImpl(dto).validateDto(validator);

    // verificationToken et mise a jour password
    IMessageResponse response = authService.reinitializeLostPassword(dto);
   return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/generate-csrf")
  ResponseEntity<IMessageResponse> csrfToken(HttpServletRequest request, HttpServletResponse response) {
    securityService.generateCsrf(request, response);

    return new ResponseEntity<>(new MessageResponseImpl("essaie"), HttpStatus.OK);
  }

  @GetMapping("/generate-captcha")
  ResponseEntity<ICaptcha> generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ICaptcha captcha = securityService.generateCaptcha(request, response);
    return new ResponseEntity<>(captcha, HttpStatus.OK);
  }

  @GetMapping("/app-param")
  ResponseEntity<IAppParam> getAppParam() {
    // Récupération csrf accesskey
    var headers = securityService.generateCsrfAccessKey();

    // Recupération captcha accesskey
    headers = securityService.generateCaptchaAccessKey(headers);
    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(authService.getAppParamter());
  }

  @PostMapping("/logout")
  ResponseEntity<IMessageResponse> logout(@RequestBody LogoutDto dto) {
    // Suppression CRF accessKey et Capctcha accessKey
    var headers = securityService.clearCsrfAccessKey();

    // Suppression Captcha access key
    headers= securityService.clearCaptchaAccessKey(headers);

    IMessageResponse response = authService.logout(dto);
    return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new MessageResponseImpl(response.getResponseMessage()));
  }
}
