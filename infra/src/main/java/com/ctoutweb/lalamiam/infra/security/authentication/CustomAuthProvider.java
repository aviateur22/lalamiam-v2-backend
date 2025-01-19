package com.ctoutweb.lalamiam.infra.security.authentication;

import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.mapper.UserEntityMapper;
import com.ctoutweb.lalamiam.infra.model.IUserLoginStatus;
import com.ctoutweb.lalamiam.infra.model.email.HtmlTemplateType;
import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import com.ctoutweb.lalamiam.infra.service.*;
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
  private final UserDetailsService userDetailsService;
  private final ILoginService loginService;
  private final IMessageService messageService;
  private final UserEntityMapper userEntityMapper;
  private final IEmailService mailService;
  private final ICryptoService cryptoService;
  @Value("${application.name}")
  private String applicationName;
  public CustomAuthProvider(
          PasswordEncoder passwordEncoder,
          UserDetailsService userDetailsService,
          ILoginService loginService,
          IMessageService messageService,
          UserEntityMapper userEntityMapper,
          IEmailService mailService,
          ICryptoService cryptoService) {
    this.userDetailsService = userDetailsService;
    this.loginService = loginService;
    this.messageService = messageService;
    this.userEntityMapper = userEntityMapper;
    this.mailService = mailService;
    this.cryptoService = cryptoService;
  }
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthException {

    validateAuthenticationRequest(authentication);

    // Recherche Utilisateur
    UserPrincipal userPrincipal = findUser(authentication.getName());

    // Vérification mot de passe
    boolean isAuthenticationValid = this.validatePassword(authentication.getCredentials().toString(), userPrincipal.password());

    // Vérification si connexion autorisé
    IUserLoginStatus userLoginStatus = validateLoginAuthorization(userPrincipal.id());

    if(!userLoginStatus.isLoginAuthorize())
      handleLoginNotAuthorize(userLoginStatus);

    // Mise a jour des information de connexion
    loginService.updateUserLoginInformation(userPrincipal.id(), isAuthenticationValid);

    if(!isAuthenticationValid)
      handleFailedLogin(userPrincipal);

    return new UsernamePasswordAuthenticationToken(userPrincipal, authentication.getCredentials().toString());
  }
  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  public void validateAuthenticationRequest(Authentication authentication) {

    if(authentication.getCredentials() == null)
      throw new AuthException(messageService.getMessage("email.unvalid"));

  }

  public UserPrincipal findUser(String userName) throws AuthException {
    UserDetails user = userDetailsService.loadUserByUsername(userName);
    // Récupération utilisateur
    if(user == null)
      throw new AuthException(messageService.getMessage("email.unvalid"));

    return (UserPrincipal) user;
  }

  /**
   * Vérification si un user peut se connecter (pas de délais d'attente en cours)
   * @param loginUserId Long - Id de la personne qui se connecte
   * @throws AuthException
   */
  public IUserLoginStatus validateLoginAuthorization(Long loginUserId) throws AuthException {
    return loginService.isLoginAuthorize(loginUserId);
  }

  /**
   * Validation du mot de passe
   * @param presentedPAssword String - Mot de passe en claire
   * @param cryptoPassword String - HAsh password
   * @return Boolean
   */
  public boolean validatePassword(String presentedPAssword, String cryptoPassword) {
    return cryptoService.isHashValid(presentedPAssword, cryptoPassword);
  }

  /**
   * Gestion d'une Authentification en echec (Erreur de mot de passe)
   * @param loginUser UserPrincipal -  Personne qui se connecte
   * @throws AuthException
   */
  public void handleFailedLogin(UserPrincipal loginUser) throws AuthException {
    // List des dernieres connexions du client
    List<LoginEntity> lastUserLoginList = loginService.getLastUserLoginInformation(loginUser.id());

    // Nombre de tentative de connexion disponible
    int loginRemainingCount = loginService.getUserRemainingLogin(lastUserLoginList);

    if(isDelayOnLoginToAdd(lastUserLoginList))
      handleAddingLoginDelay(loginUser, lastUserLoginList);

    throw new AuthException(getExceptionMessage(loginRemainingCount));
  }

  /**
   * Gestion d'une authentication non autrorisé (Delais d'attente avant prochaine connexion)
   * @param userLoginStatus
   * @throws AuthException
   */
  public void handleLoginNotAuthorize(IUserLoginStatus userLoginStatus) throws AuthException  {
    // Heure de reprise
    ZonedDateTime recoveryLoginTime = userLoginStatus.getRecoveryLoginTime();

    // Generation d'un message d'erreur avec heure de reprise
    String errorMessage = replaceWordInText(
            messageService.getMessage("login.not.authorize"),
            "!%!recoveryLoginTime!%!",
            toDateHour(recoveryLoginTime)
    );
    throw new AuthException(errorMessage);
  }

  /**
   * Gestion de l'ajout d'un délais de connexion en cas d'erreur multiple
   * @param userLogin UserPrincipal - Personne se connectant
   * @param lastUserLogins List<LoginEntity> - Liste des dernière connexion
   */
  public void handleAddingLoginDelay(UserPrincipal userLogin, List<LoginEntity> lastUserLogins) {
    // Ajout d'un temps blocant le login
    loginService.addDelayOnLogin(userLogin.id(), lastUserLogins);

    // Reset des données de connexion du client
    loginService.resetUserConnexionStatus(lastUserLogins);

    // envoi d'un email
    sendEmail(userLogin.email());
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
  public void sendEmail(String loginUserEmail) {
    // Generation d'un email d'alerte
    Map<String, String> listWordsToReplaceInHtmlTemplate = new HashMap<>();

    listWordsToReplaceInHtmlTemplate.put("year", String.valueOf(LocalDateTime.now().getYear()));
    listWordsToReplaceInHtmlTemplate.put("email", loginUserEmail);
    listWordsToReplaceInHtmlTemplate.put("email", loginUserEmail);
    listWordsToReplaceInHtmlTemplate.put("appName", applicationName.toUpperCase());

    mailService
            .setEmailInformation(HtmlTemplateType.LOGIN_CONNEXION_ALERT, loginUserEmail)
            .replaceWordInHtmlTemplate(HtmlTemplateType.LOGIN_CONNEXION_ALERT, listWordsToReplaceInHtmlTemplate)
            .sendEmail();
  }
}
