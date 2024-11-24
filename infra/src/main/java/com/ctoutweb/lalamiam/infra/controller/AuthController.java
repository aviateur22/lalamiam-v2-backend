package com.ctoutweb.lalamiam.infra.controller;

import com.ctoutweb.lalamiam.core.provider.IMessageService;
import com.ctoutweb.lalamiam.infra.dto.RegisterClientDto;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IMessageResponse;
import com.ctoutweb.lalamiam.infra.security.jwt.IJwtIssue;
import com.ctoutweb.lalamiam.infra.service.ICookieService;
import com.ctoutweb.lalamiam.infra.service.IJwtService;
import jakarta.validation.Validator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.COOKIE_CSRF_GENERATE_PARAM_NAME;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final Factory factory;
  private final Validator validator;
  private final IMessageService messageService;
  private final ICookieService cookieService;
  private final IJwtService jwtService;

  public AuthController(Factory factory, IMessageService messageService, Validator validator, ICookieService cookieService, IJwtService jwtService) {
    this.factory = factory;
    this.messageService = messageService;
    this.validator = validator;
    this.cookieService = cookieService;
    this.jwtService = jwtService;
  }

  @PostMapping("/register-client")
  ResponseEntity<IMessageResponse> registerClient(@RequestBody RegisterClientDto registerClientDto) {

    // Validation dto
    factory.getImpl(registerClientDto).validateDto(validator);

    return new ResponseEntity<>(factory.getMessageResponseImpl(messageService.getMessage("register.success")), HttpStatus.OK);
  }

  @GetMapping("/csrf")
  ResponseEntity<String> csrfToken() {
    return new ResponseEntity<>("CSRF TOKEN", HttpStatus.OK);
  }

  @GetMapping("/initialization")
  ResponseEntity<String> initialization() {
    HttpHeaders headers = new HttpHeaders();
    IJwtIssue jwt = jwtService.generate();
    headers.add(HttpHeaders.SET_COOKIE, cookieService.generateCookie(COOKIE_CSRF_GENERATE_PARAM_NAME, jwt.getJwtToken()));
    return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("cookie ok");
  }
}
