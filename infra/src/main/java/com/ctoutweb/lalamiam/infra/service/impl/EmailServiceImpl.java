package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;
import com.ctoutweb.lalamiam.infra.service.IEmailService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailServiceImpl implements IEmailService {
  @Override
  public void sendEmail(String mailSubject, String to, String mailContent, String exceptionMessage) {

  }

  @Override
  public String generateHtml(HtmlTemplateType type, Map<String, String> wordsToReplaceInHtmlTemplate) {
    return null;
  }
}
