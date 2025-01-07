package com.ctoutweb.lalamiam.infra.security.authentication;

import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.exception.BadRequestException;
import com.ctoutweb.lalamiam.infra.mapper.UserEntityMapper;
import com.ctoutweb.lalamiam.infra.model.IUserLoginStatus;
import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;
import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.IEmailService;
import com.ctoutweb.lalamiam.infra.service.ILoginService;
import com.ctoutweb.lalamiam.infra.service.IMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.LOGIN_ERROR_ATTEMPT_AVAILABLE;
import static com.ctoutweb.lalamiam.infra.utility.DateUtility.toDateHour;
import static com.ctoutweb.lalamiam.infra.utility.TextUtility.replaceWordInText;

@Component
public class CustomAuthProvider implements AuthenticationProvider {
  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  private final ILoginService loginService;
  private final IMessageService messageService;
  private final UserEntityMapper userEntityMapper;
  private final IEmailService mailService;
  @Value("${application.name}")
  private String applicationName;
  public CustomAuthProvider(
          PasswordEncoder passwordEncoder,
          UserDetailsService userDetailsService,
          ILoginService loginService,
          IMessageService messageService,
          UserEntityMapper userEntityMapper,
          IEmailService mailService) {

    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.loginService = loginService;
    this.messageService = messageService;
    this.userEntityMapper = userEntityMapper;
    this.mailService = mailService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthException {
    // Authentification utilisateur
    boolean isAuthenticationValid = authentication.getCredentials() != null;

    // List des dernieres connexions du client
    List<LoginEntity> lastUserLoginList = List.of();

    // Client souhaitant se connecter
    UserEntity loginUser = null;

    String presentedPassword = authentication.getCredentials().toString();
    UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());

    // Récupération utilisateur
    if(user == null)
      throw new BadRequestException(messageService.getMessage("email.unvalid"));

    // Vérification MDP
    UserPrincipal userLogin = (UserPrincipal) user;
    loginUser = userEntityMapper.map((UserPrincipal) user);

    // Vérification si connxion au compte possible
    IUserLoginStatus userLoginStatus = loginService.isLoginAuthorize(loginUser.getId());

    if(!userLoginStatus.isLoginAuthorize()) {
      // Heure de reprise
      ZonedDateTime recoveryLoginTime = userLoginStatus.getRecoveryLoginTime();

      // Generation d'un message d'erreur avec heur de reprise
      String errorMessage = replaceWordInText(
              messageService.getMessage("login.not.authorize"),
              "!%!recoveryLoginTime!%!",
              toDateHour(recoveryLoginTime)
      );
      throw new AuthException(errorMessage);
    }

    // Vérification mot de passe
    isAuthenticationValid = this.passwordEncoder.matches(presentedPassword, user.getPassword());

    // Mise a jour des informations de connexion du client
    lastUserLoginList = loginService.updateUserLoginInformation(loginUser, isAuthenticationValid);

    // Vérification si délai de connexion à rajouter
    if(!isAuthenticationValid) {
      // Nombre de tentative de connexion disponible
      int loginRemainingCount = loginService.getUserRemainingLogin(lastUserLoginList);

      if(isDelayOnLoginToAdd(lastUserLoginList)) {
        // Ajout d'un temps blocant le login
        loginService.addDelayOnLogin(loginUser, lastUserLoginList);

        // Reset des données de connexion du client
        loginService.resetUserConnexionStatus(lastUserLoginList);

        // envoi d'un email
        sendEmail(loginUser);
      }
      throw new AuthException(getExceptionMessage(loginRemainingCount));
    }

    loginService.resetUserConnexionStatus(lastUserLoginList);
    return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials().toString());


  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  /**
   * Vérification si un temps d'attente doit être mis à la connexion
   * @param lastUserLoginList List<UserLoginEntity>
   * @return boolean
   */
  public boolean isDelayOnLoginToAdd(List<LoginEntity> lastUserLoginList) {
    long loginAttemptErrorCount = lastUserLoginList.stream()
            .filter(login-> !login.getIsLoginSuccess() && login.getHasToBeCheck())
            .count();

    return loginAttemptErrorCount >= LOGIN_ERROR_ATTEMPT_AVAILABLE;
  }


  /**
   * Message sur le nombre de connexion disponible
   * @param remainigLogin Integer - Nombre de conexion disponible en cas d'erreur
   * @return String
   */
  public String getExceptionMessage(int remainigLogin) {

    return switch (remainigLogin) {
      case 4 -> messageService.getMessage("email.unvalid");
      case 3,2 -> messageService.getMessage("email.unvalid.attempt.message").replace("!%!number!%!", String.valueOf(remainigLogin));
      case 1 -> messageService.getMessage("email.unvalid.last.attempt.message");
      case 0 -> messageService.getMessage("email.unvalid.login.delay");
      default-> messageService.getMessage("email.unvalid");
    };
  }

  public void sendEmail(UserEntity user) {

    // Generation d'un email d'alerte
    Map<String, String> listWordsToReplaceInHtmlTemplate = new HashMap<>();

    listWordsToReplaceInHtmlTemplate.put("year", String.valueOf(LocalDateTime.now().getYear()));
    listWordsToReplaceInHtmlTemplate.put("email", user.getEmail());
    listWordsToReplaceInHtmlTemplate.put("email", user.getEmail());
    listWordsToReplaceInHtmlTemplate.put("appName", applicationName.toUpperCase());

    // Sujet du mail
    String emailSubject = messageService.getMessage("email.login.account.failed.subject");
    emailSubject =  replaceWordInText(emailSubject, "!%!applicationName!%!", applicationName.toUpperCase());

    String templateHtml = mailService.generateHtml(HtmlTemplateType.LOGIN_CONNEXION_ALERT, listWordsToReplaceInHtmlTemplate);
    mailService.sendEmail(
            emailSubject,
            user.getEmail(),
            templateHtml,
            messageService.getMessage("mailing.error")
    );

  }

}
