package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.core.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.model.captcha.ICaptcha;
import com.ctoutweb.lalamiam.infra.model.captcha.IUserCaptchaResponse;
import com.ctoutweb.lalamiam.infra.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SecurityServiceImpl implements ISecurityService {

  private final ICsrfService csrfService;
  private final ICaptchaService captchaService;

  public SecurityServiceImpl(ICsrfService csrfService, ICaptchaService captchaService) {
    this.csrfService = csrfService;
    this.captchaService = captchaService;
  }

  @Override
  public void generateCsrf(HttpServletRequest request, HttpServletResponse response) {
   csrfService.generateCsrf(request, response);
  }

  @Override
  public HttpHeaders generateCsrfAccessKey() {
    return csrfService.generateCsrfAccessKey();
  }

  @Override
  public boolean isCsrfValid(String csrf) {
    return false;
  }

  @Override
  public HttpHeaders generateCaptchaAccessKey() {
    return captchaService.generateCaptchaAccessKey();
  }

  @Override
  public HttpHeaders generateCaptchaAccessKey(HttpHeaders headers) {
    return captchaService.generateCaptchaAccessKey(headers);
  }

  @Override
  public ICaptcha generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
   ICaptcha captcha = captchaService.generateRandomCapatcha(request, response);

    if(captcha == null)
      throw new BadRequestException("Pas de génération de captcha");

    return captcha;
  }

  @Override
  public boolean isUserCaptchaResponseValid(IUserCaptchaResponse captchaResponse) {
    return captchaService.isCaptchaReponseValid(captchaResponse);
  }
}
