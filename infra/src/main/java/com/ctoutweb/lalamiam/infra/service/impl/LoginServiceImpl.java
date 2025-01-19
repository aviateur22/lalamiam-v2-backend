package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.exception.AuthException;
import com.ctoutweb.lalamiam.infra.factory.EntityFactory;
import com.ctoutweb.lalamiam.infra.factory.Factory;
import com.ctoutweb.lalamiam.infra.mapper.LoginEntityMapper;
import com.ctoutweb.lalamiam.infra.model.IUserLoginStatus;
import com.ctoutweb.lalamiam.infra.repository.IDelayLoginRepository;
import com.ctoutweb.lalamiam.infra.repository.ILoginRepository;
import com.ctoutweb.lalamiam.infra.repository.entity.DelayLoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import com.ctoutweb.lalamiam.infra.service.ILoginService;
import com.ctoutweb.lalamiam.infra.utility.DateUtility;
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
  private final LoginEntityMapper loginEntityMapper;
  private final Factory factory;
  private final EntityFactory entityFactory;

  @Value("${zone.id}")
  String zoneId;

  public LoginServiceImpl(
          IDelayLoginRepository delayLoginRepository,
          ILoginRepository userLoginRepository,
          LoginEntityMapper loginEntityMapper, Factory factory, EntityFactory entityFactory) {
    this.delayLoginRepository = delayLoginRepository;
    this.loginRepository = userLoginRepository;
    this.loginEntityMapper = loginEntityMapper;
    this.factory = factory;
    this.entityFactory = entityFactory;
  }

  @Override
  public void updateUserLoginInformation(Long userId, boolean isAuthenticationValid) {
    // Ajout des données de la connexion client
    ZonedDateTime loginTime = LocalDateTime.now().atZone(ZoneId.of(zoneId));

    loginRepository.save(loginEntityMapper.map(userId, loginTime, isAuthenticationValid));
  }

  @Override
  public List<LoginEntity> getLastUserLoginInformation(Long userId) {

    UserEntity loginUser =  entityFactory.getUserEntityFromId(userId);

    // Récupération des 3 dernieres connexions
    List<LoginEntity> getUserLastLoginList = loginRepository.findByUserOrderByLoginAtDesc(loginUser).stream().limit(USER_LAST_LOGIN_COUNT).toList();

    return getUserLastLoginList;
  }

  @Override
  public IUserLoginStatus isLoginAuthorize(Long userId) throws AuthException {
    ZonedDateTime checkLogintime = LocalDateTime.now().atZone(ZoneId.of(zoneId));

    DelayLoginEntity findUserDelayLogin = delayLoginRepository.findFirstByUserId(userId).orElse(null);

    if(findUserDelayLogin == null) {
      return factory.getImpl(true, null);
    }

    findUserDelayLogin.setDelayLoginUntil(DateUtility.uctToZonedDateTime(ZoneId.of(zoneId),findUserDelayLogin.getDelayLoginUntil()));
   ZonedDateTime recoveryLoginTime = findUserDelayLogin.getDelayLoginUntil();

    // La connexion est bloquée si checkLogintime < recoveryLoginTime
    if(checkLogintime.isBefore(recoveryLoginTime)) {
      return factory.getImpl(false, recoveryLoginTime);
    }

    // Suppression de l'heure de blocage
    delayLoginRepository.delete(findUserDelayLogin);
    return factory.getImpl(true, null);
  }

  @Override
  public void addDelayOnLogin(Long loginUserId, List<LoginEntity> lastUserLoginList) {

    // Calcul heure de déblocage de connexion
    ZonedDateTime loginDelayUntil = LocalDateTime.now()
            .plusMinutes(LOGIN_DELAY_IN_MINUTE)
            .truncatedTo(ChronoUnit.MINUTES)
            .atZone(ZoneId.of(zoneId));

    // Recherche si existence d'un DelayLoginEntity
    DelayLoginEntity findUserDelayLogin =  delayLoginRepository.findFirstByUserId(loginUserId).orElse(null);

    if(findUserDelayLogin != null) {
      findUserDelayLogin.setDelayLoginUntil(loginDelayUntil);
      delayLoginRepository.save(findUserDelayLogin);
      return;
    }

    DelayLoginEntity delayLogin = entityFactory.getDelayLoginEntity(loginUserId, loginDelayUntil);
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
