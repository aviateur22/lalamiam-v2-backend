package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.model.IUserLoginStatus;
import com.ctoutweb.lalamiam.infra.model.impl.UserLoginStatusImpl;
import com.ctoutweb.lalamiam.infra.repository.IDelayLoginRepository;
import com.ctoutweb.lalamiam.infra.repository.ILoginRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.DelayLoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.ILoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.*;

@Service
@PropertySource({"classpath:application.properties"})
public class LoginServiceImpl implements ILoginService {
  private static final Logger LOGGER = LogManager.getLogger();
  private final IDelayLoginRepository delayLoginRepository;
  private final ILoginRepository loginRepository;
  private final Factory factory;

  @Value("${zone.id}")
  String zoneId;

  public LoginServiceImpl(
          IDelayLoginRepository delayLoginRepository,
          ILoginRepository userLoginRepository,
          Factory factory) {
    this.delayLoginRepository = delayLoginRepository;
    this.loginRepository = userLoginRepository;
    this.factory = factory;
  }

  @Override
  public List<LoginEntity> updateUserLoginInformation(UserEntity user, boolean isAuthenticationValid) {
    // Ajout des données de la connexion client
    ZonedDateTime loginTime = LocalDateTime.now().atZone(ZoneId.of(zoneId));

    LoginEntity loginEntity = new LoginEntity();
    loginEntity.setLoginAt(loginTime);
    loginEntity.setIsLoginSuccess(isAuthenticationValid);
    loginEntity.setUser(user);
    loginEntity.setHasToBeCheck(true);
    loginRepository.save(loginEntity);

    // Récupération des 3 dernieres connexions
    List<LoginEntity> getUserLastLoginList = loginRepository.findByUserOrderByLoginAtDesc(user).stream().limit(USER_LAST_LOGIN_COUNT).toList();

    return getUserLastLoginList;
  }

  @Override
  public IUserLoginStatus isLoginAuthorize(Long userId) throws AuthException {
    ZonedDateTime checkLogintime = LocalDateTime.now().atZone(ZoneId.of(zoneId));

    DelayLoginEntity findUserDelayLogin = delayLoginRepository.findFirstByUserId(userId).orElse(null);

    if(findUserDelayLogin == null) {
      return factory.getImpl(true, null);
    }

   ZonedDateTime recoveryLoginTime = findUserDelayLogin.getDelayLoginUntil();

    // La connexion est bloquée si checkLogintime < recoveryLoginTime
    if(checkLogintime.isBefore(recoveryLoginTime)) {
      return new UserLoginStatusImpl(false, recoveryLoginTime);

    }

    // Suppression de l'heure de blocage
    delayLoginRepository.delete(findUserDelayLogin);
    return factory.getImpl(true, null);
  }

  @Override
  public void addDelayOnLogin(UserEntity user, List<LoginEntity> lastUserLoginList) {

    // Calcul heure de déblocage de connexion
    ZonedDateTime loginDelayUntil = LocalDateTime.now()
            .plusMinutes(LOGIN_DELAY)
            .truncatedTo(ChronoUnit.MINUTES)
            .atZone(ZoneId.of(zoneId));

    // Recherche si existence d'un DelayLoginEntity
    DelayLoginEntity findUserDelayLogin =  delayLoginRepository.findFirstByUserId(user.getId()).orElse(null);

    if(findUserDelayLogin != null) {
      findUserDelayLogin.setDelayLoginUntil(loginDelayUntil);
      delayLoginRepository.save(findUserDelayLogin);
      return;
    }

    DelayLoginEntity delayLogin = new DelayLoginEntity();
    delayLogin.setDelayLoginUntil(loginDelayUntil);
    delayLogin.setUser(user);
    delayLoginRepository.save(delayLogin);
  }
  @Override
  public Integer getUserRemainingLogin(List<LoginEntity> lastUserLoginList) {
    // Récupérartion du nombre de connexion invalide
    long loginAttemptErrorCount = lastUserLoginList
            .stream()
            .filter(login-> !login.getIsLoginSuccess() && login.getHasToBeCheck())
            .count();

    // Calcul du nombre de connexion restante en cas d'erreur
    return (int) (LOGIN_ERROR_ATTEMPT_AVAILABLE - loginAttemptErrorCount);
  }

  /**
   * Mise a jour des données de connexion si
   * @param lastUserLoginList List<UserLoginEntity> - Liste des 3 dernieres connexions
   */
  public void resetUserConnexionStatus(List<LoginEntity> lastUserLoginList) {

    lastUserLoginList.forEach(userLogin->{
      userLogin.setHasToBeCheck(false);
      loginRepository.save(userLogin);
    });
  }

}
