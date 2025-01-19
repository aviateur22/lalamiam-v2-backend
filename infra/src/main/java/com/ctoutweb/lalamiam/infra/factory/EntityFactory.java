package com.ctoutweb.lalamiam.infra.factory;

import com.ctoutweb.lalamiam.infra.repository.entity.DelayLoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.LoginEntity;
import com.ctoutweb.lalamiam.infra.repository.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class EntityFactory {

  public UserEntity getUserEntityFromId(Long id) {
    UserEntity user = new UserEntity();
    user.setId(id);
    return user;
  }

  public LoginEntity getLoginEntityFrom(Long userId, ZonedDateTime loginTime,boolean isAuthencationValid, boolean hasToBeCheck) {
    UserEntity loginUser = getUserEntityFromId(userId);

    LoginEntity loginEntity = new LoginEntity();
    loginEntity.setHasToBeCheck(hasToBeCheck);
    loginEntity.setLoginAt(loginTime);
    loginEntity.setUser(loginUser);
    loginEntity.setIsLoginSuccess(isAuthencationValid);

    return loginEntity;
  }

  public DelayLoginEntity getDelayLoginEntity(Long loginUserId, ZonedDateTime loginDelayUntil) {
    UserEntity loginUser = getUserEntityFromId(loginUserId);

    DelayLoginEntity delayLogin = new DelayLoginEntity();
    delayLogin.setDelayLoginUntil(loginDelayUntil);
    delayLogin.setUser(loginUser);

    return delayLogin;
  }
}
