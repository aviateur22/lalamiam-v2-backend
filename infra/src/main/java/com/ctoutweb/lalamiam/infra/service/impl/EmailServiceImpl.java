package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.model.IApiLanguage;
import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;
import com.ctoutweb.lalamiam.infra.service.IEmailService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import com.ctoutweb.lalamiam.infra.utility.FileUtility;
import com.ctoutweb.lalamiam.infra.utility.TextUtility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.HTML_TEMPLATE_FOLDER;
import static com.ctoutweb.lalamiam.infra.utility.TextUtility.replaceWordInText;

@Service
public class EmailServiceImpl implements IEmailService {
  private static final Logger LOGGER = LogManager.getLogger();
  private final IApiLanguage apiLanguage;
  private final IMessageService messageService;
  private final JavaMailSender mailSender;
  @Value("${application.name}")
  private String applicationName;

  // Destinataire email
  private String to;
  //Sujet email
  private String mailSubject;

  //Contenu de lemail
  private String mailContent;

  public EmailServiceImpl(IApiLanguage apiLanguage, IMessageService messageService, JavaMailSender mailSender) {
    this.apiLanguage = apiLanguage;
    this.messageService = messageService;
    this.mailSender = mailSender;
  }

  @Override
  public IEmailService replaceWordInHtmlTemplate(HtmlTemplateType type, Map<String, String> wordsToReplaceInHtmlTemplate) {

    // Récupration du template HTML en fonction du type de mail et du language
    String fileName = switch (type) {
      case ACCOUNT_ACTIVATION -> HtmlTemplateType.ACCOUNT_ACTIVATION.getFileName(apiLanguage.getValidatedLanguage());
      case CHANGE_PASSWORD -> HtmlTemplateType.CHANGE_PASSWORD.getFileName(apiLanguage.getValidatedLanguage());
      case LOGIN_CONNEXION_ALERT -> HtmlTemplateType.LOGIN_CONNEXION_ALERT.getFileName(apiLanguage.getValidatedLanguage());
      case PROFESSIONAL_REGISTER_ACCOUNT_CONFIRMATION -> HtmlTemplateType.PROFESSIONAL_REGISTER_ACCOUNT_CONFIRMATION.getFileName(apiLanguage.getValidatedLanguage());
    };

    String filePath = HTML_TEMPLATE_FOLDER + fileName;
    LOGGER.debug(()->String.format("TemplateHTML name: %s , path: %s", fileName, filePath));

    // Lecture du contenu template
    String content = FileUtility.readFile(filePath);

    // Remplacement Contenu
    if(wordsToReplaceInHtmlTemplate == null){
      this.mailContent = content;
      return this;
    }

    this.mailContent = TextUtility.replaceWordInText(content, wordsToReplaceInHtmlTemplate);
    return this;
  }

  @Override
  public IEmailService setEmailInformation(HtmlTemplateType type, String to) {
    // Destinataire du mail
    this.to = to;

    // Récupration de l'email subject en fonction du type de mail et du language
    String emailSubject = switch (type) {
      case ACCOUNT_ACTIVATION -> messageService.getMessage("email.register.subject");
      case CHANGE_PASSWORD -> messageService.getMessage("email.reinitialize.password.subject");
      case LOGIN_CONNEXION_ALERT -> messageService.getMessage("email.login.account.failed.subject");
      case PROFESSIONAL_REGISTER_ACCOUNT_CONFIRMATION -> messageService.getMessage("email.register.professional.subject");
    };

    this.mailSubject = replaceWordInText(emailSubject, "!%!applicationName!%!", applicationName.toUpperCase());

    return this;
  }

  @Override
  public void sendEmail() {
    new Thread(()->{
      try{
        LOGGER.debug("Language pour l'envoie des emails :" + apiLanguage.getValidatedLanguage());
        LOGGER.debug("Envoie email");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setText(mailContent, true);
        helper.setTo(to);
        helper.setSubject(mailSubject);
        helper.setFrom("MonApp");
        mailSender.send(message);
        LOGGER.debug("Envoie email terminé");
      } catch (MessagingException exception) {
        LOGGER.error("Erreur envoie email: " + exception);
      }
    }).start();
  }
}
