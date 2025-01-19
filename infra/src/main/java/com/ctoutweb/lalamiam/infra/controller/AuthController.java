package com.ctoutweb.lalamiam.infra.controller;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.infra.dto.LoginDto;
import com.ctoutweb.lalamiam.infra.dto.LoginResponseDto;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.dto.RegisterProfessionalDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

  @PostMapping("/register-professional")
  ResponseEntity<IMessageResponse> registerProfessional(@RequestBody RegisterProfessionalDto registerProfessionalDto) {
    // Validation dto
    factory.getImpl(registerProfessionalDto).validateDto(validator);

    // Validation captcha
    if(!securityService.isUserCaptchaResponseValid(registerProfessionalDto.userCaptchaResponse()))
      throw new BadRequestException(messageService.getMessage("captcha.invalid.response"));

    // Creation compte client
    authService.registerProfessional(registerProfessionalDto);

    return new ResponseEntity<>(factory.getMessageResponseImpl(messageService.getMessage("register.success")), HttpStatus.OK);
  }

  @PostMapping("/login")
  ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
    factory.getImpl(loginDto).validateDto(validator);

    LoginResponseDto loginResponseDto = authService.login(loginDto);

    return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
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
}
