package com.ctoutweb.lalamiam.infra.controller;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.factory.Factory;
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

    return new ResponseEntity<>(factory.getMessageResponseImpl(messageService.getMessage("register.success")), HttpStatus.OK);
  }

  @GetMapping("/generate-csrf")
  ResponseEntity<String> csrfToken(HttpServletRequest request, HttpServletResponse response) {
    securityService.generateCsrf(request, response);
    return new ResponseEntity<>("CSRF TOKEN", HttpStatus.OK);
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
