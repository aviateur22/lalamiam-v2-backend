package com.ctoutweb.lalamiam.infra.service.impl;

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
  public ICaptcha generateCaptcha() throws IOException {
    return captchaService.generateRandomCapatcha();
  }

  @Override
  public boolean isUserCaptchaResponseValid(IUserCaptchaResponse captchaResponse) {
    return false;
  }
}
